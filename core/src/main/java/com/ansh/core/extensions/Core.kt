package com.ansh.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.ansh.core.CoreApp

val Context.coreApp: CoreApp
    get() = applicationContext as CoreApp

fun Activity.open(cls: Class<*>, finish: Boolean = false) {
    val intent = Intent(this, cls)
    this.startActivity(intent)
    if (finish) this.finish()
}

val Int.toDP
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        CoreApp.appCtx.resources.displayMetrics
    )

val Int.resToStr: String
    get() = CoreApp.appCtx.getString(this)

val Int.resToList: List<String>
    get() = CoreApp.appCtx.resources.getStringArray(this).toList()

val Int.resToColor: Int
    get() = ContextCompat.getColor(CoreApp.appCtx, this)

val Int.resToDrawable: Drawable?
    get() = ContextCompat.getDrawable(CoreApp.appCtx, this)