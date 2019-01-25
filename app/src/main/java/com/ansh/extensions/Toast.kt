package com.ansh.extensions

import android.widget.Toast
import com.ansh.CoreApp

private const val tag = "ToastExtension"

val String.toast
    get() = Toast.makeText(CoreApp.appCtx, this, Toast.LENGTH_SHORT).show()

val String.toastLong
    get() = Toast.makeText(CoreApp.appCtx, this, Toast.LENGTH_LONG).show()