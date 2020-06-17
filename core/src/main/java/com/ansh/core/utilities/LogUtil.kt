package com.ansh.core.utilities

import android.util.Log

object LogUtil {

    fun d(TAG: String, m: String) {
        if (com.ansh.core.CoreApp.debuggingEnabled) {
            Log.d(TAG, "" + m)
        }
    }

    fun e(TAG: String, m: String) {
        if (com.ansh.core.CoreApp.debuggingEnabled) {
            Log.e(TAG, "" + m)
        }
    }

    fun e(TAG: String, m: String, t: Throwable) {
        if (com.ansh.core.CoreApp.debuggingEnabled) {
            Log.e(TAG, "" + m, t)
        }
    }

    fun i(TAG: String, m: String) {
        if (com.ansh.core.CoreApp.debuggingEnabled) {
            Log.i(TAG, "" + m)
        }
    }
}
