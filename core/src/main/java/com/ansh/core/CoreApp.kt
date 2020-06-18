package com.ansh.core

import android.app.Application
import android.content.Context

open class CoreApp : Application() {

    companion object {
        private var coreApp: CoreApp? = null

        val appCtx: Context
            get() = coreApp!!.applicationContext

        var debuggingEnabled = true
        var baseURL = ""
        var preferenceName = ""
    }

    override fun onCreate() {
        super.onCreate()
        coreApp = this
    }

    fun setup(debug: Boolean, prefName: String) {
        debuggingEnabled = debug
        preferenceName = prefName
    }
}
