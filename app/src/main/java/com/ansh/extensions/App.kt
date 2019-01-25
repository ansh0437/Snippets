package com.ansh.extensions

import android.content.Context
import android.util.TypedValue
import com.ansh.CoreApp

private const val tag = "ContextExtension"

val Context.coreApp: CoreApp
    get() = applicationContext as CoreApp

val Int.toDP
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        CoreApp.appCtx.resources.displayMetrics
    )
