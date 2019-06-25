package com.ansh.view.activities.base

import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun fullScreen() {
        val fullScreen = WindowManager.LayoutParams.FLAG_FULLSCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(fullScreen, fullScreen)
    }

    fun hideBar() {
        supportActionBar?.hide()
    }

}