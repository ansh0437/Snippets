package com.ansh.core.extensions

import android.util.Patterns
import java.util.regex.Pattern

val String.isValidNumber: Boolean
    get() = Patterns.PHONE.matcher(this).matches()

val String.isValidEmail: Boolean
    get() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

val String.containsNumber: Boolean
    get() = Pattern.compile("[0-9]").matcher(this).find()

val String.containsString: Boolean
    get() = Pattern.compile("[a-zA-Z]").matcher(this).find()
