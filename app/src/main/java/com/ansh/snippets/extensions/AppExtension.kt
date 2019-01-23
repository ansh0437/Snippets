package com.ansh.snippets.extensions

import android.util.Log
import android.widget.Toast
import com.ansh.snippets.BuildConfig
import com.ansh.snippets.SnippetsApp

const val tag = "AppExtension"

fun String.toClass(): Class<*>? {
    try {
        val className = String.format(
            "%s.activities.%sActivity",
            SnippetsApp.appCtx.packageName, this
        )
        return Class.forName(className)
    } catch (e: Exception) {
        e.logError(tag, "$this toClass: ")
    }
    return null
}

fun String.toast() {
    Toast.makeText(SnippetsApp.appCtx, this, Toast.LENGTH_SHORT).show()
}

fun String.toastLong() {
    Toast.makeText(SnippetsApp.appCtx, this, Toast.LENGTH_LONG).show()
}

fun String.logError(tag: String) {
    if (BuildConfig.DEBUG) Log.e(tag, this)
}

fun String.logDebug(tag: String) {
    if (BuildConfig.DEBUG) Log.d(tag, this)
}

fun String.logInfo(tag: String) {
    if (BuildConfig.DEBUG) Log.i(tag, this)
}

fun Throwable.logError(tag: String, msg: String) {
    if (BuildConfig.DEBUG) Log.e(tag, msg, this)
}