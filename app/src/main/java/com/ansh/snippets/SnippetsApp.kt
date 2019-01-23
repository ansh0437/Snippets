package com.ansh.snippets

import android.app.Application
import android.content.Context

class SnippetsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        snippetsApp = this
    }

    companion object {
        private var snippetsApp: SnippetsApp? = null
        val appCtx: Context
            get() = snippetsApp!!.applicationContext
    }
}
