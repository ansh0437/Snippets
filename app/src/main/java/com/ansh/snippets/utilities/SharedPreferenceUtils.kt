package com.ansh.snippets.utilities

import android.content.Context
import android.content.SharedPreferences
import com.ansh.snippets.R
import com.ansh.snippets.SnippetsApp

class SharedPreferenceUtils private constructor() {

    private var mSharedPreferences: SharedPreferences? = null

    init {
        init()
    }

    private fun init() {
        mSharedPreferences = SnippetsApp.appCtx.getSharedPreferences(
            SnippetsApp.appCtx.getString(R.string.app_name), Context.MODE_PRIVATE
        )
    }

    fun saveValue(key: String, value: Any) {
        val editor = mSharedPreferences!!.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
        }
        editor.apply()
    }

    fun setString(key: String, value: String) {
        mSharedPreferences!!.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String): String? {
        return mSharedPreferences!!.getString(key, defValue)
    }

    fun setInt(key: String, value: Int) {
        mSharedPreferences!!.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int): Int {
        return mSharedPreferences!!.getInt(key, defValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        mSharedPreferences!!.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mSharedPreferences!!.getBoolean(key, defValue)
    }

    companion object {

        val instance: SharedPreferenceUtils
            @Synchronized get() = SharedPreferenceUtils()

        val preferenceInstance: SharedPreferences?
            @Synchronized get() = SharedPreferenceUtils().mSharedPreferences

        /* STATICS */

        fun getStringPreference(key: String): String {
            var value = ""
            val preferences = preferenceInstance
            if (preferences != null) {
                value = preferences.getString(key, "")
            }
            return value
        }

        fun setStringPreference(key: String, value: String): Boolean {
            val preferences = preferenceInstance
            if (preferences != null && StringUtils.isNotBlank(key)) {
                val editor = preferences.edit()
                editor.putString(key, value)
                return editor.commit()
            }
            return false
        }

        fun getFloatPreference(key: String): Float {
            var value = 0F
            val preferences = preferenceInstance
            if (preferences != null) {
                value = preferences.getFloat(key, 0F)
            }
            return value
        }

        fun setFloatPreference(key: String, value: Float): Boolean {
            val preferences = preferenceInstance
            if (preferences != null) {
                val editor = preferences.edit()
                editor.putFloat(key, value)
                return editor.commit()
            }
            return false
        }

        fun getLongPreference(key: String): Long {
            var value = 0L
            val preferences = preferenceInstance
            if (preferences != null) {
                value = preferences.getLong(key, 0L)
            }
            return value
        }

        fun setLongPreference(key: String, value: Long): Boolean {
            val preferences = preferenceInstance
            if (preferences != null) {
                val editor = preferences.edit()
                editor.putLong(key, value)
                return editor.commit()
            }
            return false
        }

        fun getIntegerPreference(key: String): Int {
            var value = 0
            val preferences = preferenceInstance
            if (preferences != null) {
                value = preferences.getInt(key, 0)
            }
            return value
        }

        fun setIntegerPreference(key: String, value: Int): Boolean {
            val preferences = preferenceInstance
            if (preferences != null) {
                val editor = preferences.edit()
                editor.putInt(key, value)
                return editor.commit()
            }
            return false
        }

        fun getBooleanPreference(key: String): Boolean {
            var value = false
            val preferences = preferenceInstance
            if (preferences != null) {
                value = preferences.getBoolean(key, false)
            }
            return value
        }

        fun setBooleanPreference(key: String, value: Boolean): Boolean {
            val preferences = preferenceInstance
            if (preferences != null) {
                val editor = preferences.edit()
                editor.putBoolean(key, value)
                return editor.commit()
            }
            return false
        }
    }
}
