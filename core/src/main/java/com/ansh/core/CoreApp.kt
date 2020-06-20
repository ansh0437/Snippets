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

    fun setup(debug: Boolean, baseUrl: String, prefName: String) {
        debuggingEnabled = debug
        baseURL = baseUrl
        preferenceName = prefName
    }
}
