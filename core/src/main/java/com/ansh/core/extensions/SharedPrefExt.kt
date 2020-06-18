package com.ansh.core.extensions

import com.ansh.core.data.SharedPref

fun String.save(value: Any) = SharedPref.setValue(this, value)

fun String.getString(defaultValue: String = "") = SharedPref.getString(this, defaultValue)

fun String.getInt(defaultValue: Int = 0) = SharedPref.getInt(this, defaultValue)

fun String.getLong(defaultValue: Long = 0L) = SharedPref.getLong(this, defaultValue)

fun String.getFloat(defaultValue: Float = 0F) = SharedPref.getFloat(this, defaultValue)

fun String.getBoolean(defaultValue: Boolean = false) = SharedPref.getBoolean(this, defaultValue)
