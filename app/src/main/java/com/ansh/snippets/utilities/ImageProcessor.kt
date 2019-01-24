package com.ansh.snippets.utilities

import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import com.ansh.snippets.extensions.logError
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageProcessor {

    private val tag = ImageProcessor::class.java.simpleName

    var mTempBitmap: Bitmap? = null

    var mTempPath = ""

    private var mImagesDir: File? = null

    private fun initDirectory(ctx: Context) {
        mImagesDir = File(ctx.filesDir, "profile_images")
        if (!mImagesDir!!.exists()) {
            mImagesDir!!.mkdir()
        }
    }

    fun getUploadTempFile(ctx: Context, bmp: Bitmap): File {
        val file = File(ctx.filesDir, "temp_compressor")
        if (!file.exists()) file.mkdir()
        val upFile = File(file, "up_temp.jpeg")
        try {
            val os = FileOutputStream(upFile)
            os.write(getBytesFromBitmap(bmp))
            os.flush()
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return upFile
    }

    fun getTempFile(ctx: Context): File {
        val file = File(ctx.filesDir, "temp_compressor")
        if (!file.exists()) file.mkdir()
        return File(file, "tmp_comp.jpeg")
    }

    private fun getTempCompressorFile(ctx: Context): String {
        val file = File(ctx.filesDir, "temp_compressor")
        if (!file.exists()) file.mkdir()
        for (f in file.listFiles()) f.delete()
        return File(file, "tmp_comp.jpeg").absolutePath
    }

    fun isImagesExists(ctx: Context): Boolean {
        initDirectory(ctx)
        return mImagesDir!!.listFiles().size > 0
    }

    fun isImagesFull(ctx: Context): Boolean {
        initDirectory(ctx)
        return mImagesDir!!.listFiles().size == 5
    }

    private fun saveBytesToFile(file: String, bytes: ByteArray) {
        try {
            val os = FileOutputStream(file)
            os.write(bytes)
            os.flush()
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun saveTempFile(ctx: Context, bytes: ByteArray): String {
        val path = getTempCompressorFile(ctx)
        try {
            val os = FileOutputStream(path)
            os.write(bytes)
            os.flush()
            os.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return path
    }

    fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    fun compressImage(ctx: Context, imgBytes: ByteArray, bool: Boolean): Bitmap? {
        val filePath = getTempCompressorFile(ctx)
        var scaledBitmap: Bitmap? = null
        try {
            saveBytesToFile(filePath, imgBytes)

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)

            var actualHeight = options.outHeight
            var actualWidth = options.outWidth

            val maxHeight: Float
            val maxWidth: Float

            if (bool) {
                maxHeight = 640.0f
                maxWidth = 480.0f
            } else {
                maxHeight = 160.0f
                maxWidth = 120.0f
            }

            var imgRatio = actualWidth.toFloat() / actualHeight
            val maxRatio = maxWidth / maxHeight

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight//0.222
                    actualWidth = (imgRatio * actualWidth).toInt()//1283
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()
                }
            }
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)

            bmp = BitmapFactory.decodeFile(filePath, options)
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f

            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            val canvas = Canvas(scaledBitmap!!)
            canvas.matrix = scaleMatrix
            canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))

            val exif = ExifInterface(filePath)

            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            android.util.Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            when (orientation) {
                6 -> {
                    matrix.postRotate(90f)
                    android.util.Log.d("EXIF", "Exif: $orientation")
                }
                3 -> {
                    matrix.postRotate(180f)
                    android.util.Log.d("EXIF", "Exif: $orientation")
                }
                8 -> {
                    matrix.postRotate(270f)
                    android.util.Log.d("EXIF", "Exif: $orientation")
                }
            }
            orientation.toString().logError(tag)
            scaledBitmap =
                    Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.width, scaledBitmap.height, matrix, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return scaledBitmap
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
    }

    /* NEW */
    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        bm.recycle()
        return resizedBitmap
    }

    fun rotateImageIfRequired(context: Context, fileBytes: ByteArray): Bitmap {
        var bitmap = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.size)
        try {
            val filePath = getTempCompressorFile(context)
            saveBytesToFile(filePath, fileBytes)
            bitmap = rotateImageIfRequired(bitmap, filePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap
    }

    @Throws(IOException::class)
    private fun rotateImageIfRequired(img: Bitmap, filePath: String): Bitmap {
        val ei = ExifInterface(filePath)
        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
            else -> img
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
    }
}
