package com.ansh.core.extensions

import android.content.Context
import android.util.TypedValue

val Context.coreApp: com.ansh.core.CoreApp
    get() = applicationContext as com.ansh.core.CoreApp

val Int.toDP
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        com.ansh.core.CoreApp.appCtx.resources.displayMetrics
    )

val Int.resToStr: String
    get() = com.ansh.core.CoreApp.appCtx.getString(this)