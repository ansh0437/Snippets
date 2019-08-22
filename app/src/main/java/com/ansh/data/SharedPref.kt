package com.ansh.data

import android.content.Context
import android.content.SharedPreferences
import com.ansh.CoreApp

object SharedPref {

    private var mSharedPreferences: SharedPreferences =
        CoreApp.appCtx.getSharedPreferences(CoreApp.preferenceName, Context.MODE_PRIVATE)

    fun save(key: String, value: Any) {
        mSharedPreferences.edit().let {
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

    fun get(key: String, defValue: Any): Any? {
        return when (defValue) {
            is String -> mSharedPreferences.getString(key, defValue)
            is Int -> mSharedPreferences.getInt(key, defValue)
            is Float -> mSharedPreferences.getFloat(key, defValue)
            is Long -> mSharedPreferences.getLong(key, defValue)
            is Boolean -> mSharedPreferences.getBoolean(key, defValue)
            else -> null
        }
    }

    fun setValue(key: String, value: Any) {
        mSharedPreferences.edit().let {
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

    fun getString(key: String): String {
        return mSharedPreferences.getString(key, "") ?: ""
    }

    fun getString(key: String, defaultValue: String): String {
        return mSharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getInt(key: String): Int {
        return mSharedPreferences.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    fun getFloat(key: String): Float {
        return mSharedPreferences.getFloat(key, 0F)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return mSharedPreferences.getFloat(key, defaultValue)
    }

    fun getLong(key: String): Long {
        return mSharedPreferences.getLong(key, 0L)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    fun getBoolean(key: String): Boolean {
        return mSharedPreferences.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }
}