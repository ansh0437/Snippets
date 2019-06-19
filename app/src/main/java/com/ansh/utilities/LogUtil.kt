package com.ansh.utilities

import android.util.Log
import com.ansh.CoreApp

object LogUtil {

    fun d(TAG: String, m: String) {
        if (CoreApp.debuggingEnabled) {
            Log.d(TAG, "" + m)
        }
    }

    fun e(TAG: String, m: String) {
        if (CoreApp.debuggingEnabled) {
            Log.e(TAG, "" + m)
        }
    }

    fun e(TAG: String, m: String, t: Throwable) {
        if (CoreApp.debuggingEnabled) {
            Log.e(TAG, "" + m, t)
        }
    }

    fun i(TAG: String, m: String) {
        if (CoreApp.debuggingEnabled) {
            Log.i(TAG, "" + m)
        }
    }
}
