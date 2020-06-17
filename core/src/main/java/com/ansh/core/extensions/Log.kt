package com.ansh.core.extensions

import com.ansh.core.utilities.LogUtil

fun String.logError(tag: String) {
    LogUtil.e(tag, this)
}

fun String.logDebug(tag: String) {
    LogUtil.d(tag, this)
}

fun String.logInfo(tag: String) {
    LogUtil.i(tag, this)
}

fun Throwable.logError(tag: String, msg: String) {
    LogUtil.e(tag, msg, this)
}