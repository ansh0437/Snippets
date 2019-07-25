package com.ansh.helpers

import android.graphics.*
import androidx.exifinterface.media.ExifInterface
import com.ansh.CoreApp
import com.ansh.extensions.*
import java.io.File
import java.io.IOException
import kotlin.math.roundToInt

object PhotoHelper {

    private val tag = PhotoHelper::class.java.simpleName

    const val TEMP_IMAGE_FILE_NAME = "tempImage.jpg"
    const val ROTATION_TEMP_FILE_NAME = "rotatedTemp.jpg"
    const val COMPRESSED_TEMP_FILE_NAME = "compressedTemp.jpg"

    var mainDir = File(CoreApp.appCtx.filesDir, "photoHelper")

    init {
        mainDir.createIfNotExists()
    }

    fun saveBitmap(dir: File, fileName: String, bitmap: Bitmap) {
        val file = File(dir, fileName)
        file.saveBytes(bitmap.bytes)
    }

    fun rotateImageIfRequired(fileBytes: ByteArray): Bitmap {
        var bitmap = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.size)
        try {
            val filePath = mainDir.getTempPath(ROTATION_TEMP_FILE_NAME)
            filePath.saveBytes(fileBytes)
            bitmap = rotateImageIfRequired(bitmap, filePath)
        } catch (e: IOException) {
            e.logError(tag, "rotateImageIfRequired: ")
        }
        return bitmap
    }

    @Throws(IOException::class)
    private fun rotateImageIfRequired(bitmap: Bitmap, filePath: String): Bitmap {
        val ei = ExifInterface(filePath)
        return when (ei.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL
        )) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(
                bitmap,
                90
            )
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(
                bitmap,
                180
            )
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(
                bitmap,
                270
            )
            else -> bitmap
        }
    }

    private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
    }

    fun compressImage(imgBytes: ByteArray): Bitmap? {
        val filePath = mainDir.getTempPath(COMPRESSED_TEMP_FILE_NAME)
        var scaledBitmap: Bitmap? = null
        try {
            filePath.saveBytes(imgBytes)

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)

            var actualHeight = options.outHeight
            var actualWidth = options.outWidth

            val maxHeight = 640.0f
            val maxWidth = 480.0f

            var imgRatio = actualWidth.toFloat() / actualHeight
            val maxRatio = maxWidth / maxHeight

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                when {
                    imgRatio < maxRatio -> {
                        imgRatio = maxHeight / actualHeight
                        actualWidth = (imgRatio * actualWidth).toInt()
                        actualHeight = maxHeight.toInt()
                    }
                    imgRatio > maxRatio -> {
                        imgRatio = maxWidth / actualWidth
                        actualHeight = (imgRatio * actualHeight).toInt()
                        actualWidth = maxWidth.toInt()
                    }
                    else -> {
                        actualHeight = maxHeight.toInt()
                        actualWidth = maxWidth.toInt()
                    }
                }
            }
            options.inSampleSize = calculateInSampleSize(
                options,
                actualWidth,
                actualHeight
            )
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
            canvas.drawBitmap(
                bmp,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )

            val exif = ExifInterface(filePath)

            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            when (orientation) {
                6 -> matrix.postRotate(90f)
                3 -> matrix.postRotate(180f)
                8 -> matrix.postRotate(270f)
            }
            orientation.toString().logError(tag)
            scaledBitmap =
                Bitmap.createBitmap(
                    scaledBitmap,
                    0,
                    0,
                    scaledBitmap.width,
                    scaledBitmap.height,
                    matrix,
                    true
                )
        } catch (e: Exception) {
            e.logError(tag, "compressImage: ")
        }
        return scaledBitmap
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = height.toFloat().div(reqHeight.toFloat()).roundToInt()
            val widthRatio = width.toFloat().div(reqWidth.toFloat()).roundToInt()
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
    }
}
