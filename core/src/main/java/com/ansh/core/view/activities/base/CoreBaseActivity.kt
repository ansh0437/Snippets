package com.ansh.core.view.activities.base

import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class CoreBaseActivity : AppCompatActivity() {

    fun fullScreen() {
        val fullScreen = WindowManager.LayoutParams.FLAG_FULLSCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(fullScreen, fullScreen)
    }

    fun hideBar() {
        supportActionBar?.hide()
    }

    fun showBack() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    fun enableWindowTouch() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun disableWindowTouch() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

}