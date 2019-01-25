package com.ansh.extensions

import java.io.File
import java.io.FileOutputStream

private const val tag = "FileExtension"

fun File.createIfNotExists() {
    if (notExists) mkdir()
}

val File.notExists
    get() = !this.exists()

val String.toFile
    get() = File(this)

fun File.getTempPath(fileName: String): String {
    return File(this, fileName).absolutePath
}

fun String.saveBytes(bytes: ByteArray) {
    try {
        val os = FileOutputStream(this)
        os.write(bytes)
        os.flush()
        os.close()
    } catch (e: Exception) {
        e.logError(tag, "saveBytes: ")
    }
}