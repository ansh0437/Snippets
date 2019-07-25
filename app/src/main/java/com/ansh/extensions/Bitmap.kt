package com.ansh.extensions

import android.graphics.Bitmap
import android.graphics.Matrix
import com.ansh.helpers.PhotoHelper
import java.io.ByteArrayOutputStream
import java.io.File

val Bitmap.bytes: ByteArray
    get() = run {
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        baos.toByteArray()
    }

fun Bitmap.resize(newWidth: Int, newHeight: Int): Bitmap {
    val matrix = Matrix()
    matrix.postScale(newWidth.toFloat().div(width), newHeight.toFloat().div(height))
    val resizedBitmap = Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
    recycle()
    return resizedBitmap
}

fun Bitmap.save(dir: File, fileName: String) {
    PhotoHelper.saveBitmap(dir, fileName, this)
}