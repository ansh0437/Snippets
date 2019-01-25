package com.ansh

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

open class CoreApp : Application() {

    override fun onCreate() {
        super.onCreate()
        coreApp = this

        if (resources.getBoolean(R.bool.crashlytics_enabled)) {
            val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(BuildConfig.DEBUG)
                .build()
            Fabric.with(fabric)
        }
    }

    companion object {
        private var coreApp: CoreApp? = null
        val appCtx: Context
            get() = coreApp!!.applicationContext
    }
}
