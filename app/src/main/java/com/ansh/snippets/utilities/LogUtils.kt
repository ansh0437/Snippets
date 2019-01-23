package com.ansh.snippets.utilities

import android.util.Log
import com.ansh.snippets.BuildConfig

object LogUtils {

    fun d(TAG: String, m: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "" + m)
        }
    }

    fun e(TAG: String, m: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "" + m)
        }
    }

    fun e(TAG: String, m: String, t: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "" + m, t)
        }
    }

    fun i(TAG: String, m: String) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "" + m)
        }
    }

}
