package com.ansh.extensions

import android.util.Patterns

private const val tag = "PatternsExtension"

val String.isValidNumber: Boolean
    get() = Patterns.PHONE.matcher(this).matches()

val String.isValidEmail: Boolean
    get() = Patterns.EMAIL_ADDRESS.matcher(this).matches()