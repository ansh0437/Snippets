package com.ansh.data

import android.content.Context
import android.content.SharedPreferences
import com.ansh.CoreApp

object SharedPref {

    private var pref: SharedPreferences =
        CoreApp.appCtx.getSharedPreferences(CoreApp.preferenceName, Context.MODE_PRIVATE)

    fun setValue(key: String, value: Any) {
        pref.edit().let {
            when (value) {
                is String -> it.putString(key, value)
                is Int -> it.putInt(key, value)
                is Boolean -> it.putBoolean(key, value)
                is Float -> it.putFloat(key, value)
                is Long -> it.putLong(key, value)
            }
            it.apply()
        }
    }

    fun getString(key: String, def: String = "") = pref.getString(key, def) ?: def

    fun getInt(key: String, def: Int = 0) = pref.getInt(key, def)

    fun getFloat(key: String, def: Float = 0F) = pref.getFloat(key, def)

    fun getLong(key: String, def: Long = 0L) = pref.getLong(key, def)

    fun getBoolean(key: String, def: Boolean = false) = pref.getBoolean(key, def)

    fun clear() = pref.edit().clear().apply()

}