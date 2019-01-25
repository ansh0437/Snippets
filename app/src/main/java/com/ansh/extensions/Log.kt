package com.ansh.extensions

import android.util.Log
import com.ansh.BuildConfig

private const val tag = "LogExtension"

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