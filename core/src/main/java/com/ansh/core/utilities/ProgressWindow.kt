package com.ansh.core.utilities

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.ansh.core.R

class ProgressWindow private constructor(private val mContext: Context) {

    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null
    private var progressLayout: View? = null
    private var isAttached: Boolean = false

    private var mainProgress: ProgressBar? = null
    private var mainLayout: LinearLayout? = null

    private val handler: Handler = Handler()

    init {
        setupView()
    }

    private fun setupView() {
        val metrics = DisplayMetrics()
        windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager!!.defaultDisplay.getMetrics(metrics)
        progressLayout =
            LayoutInflater.from(mContext).inflate(R.layout.layout_progress_window, null)

        progressLayout!!.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                isAttached = true
            }

            override fun onViewDetachedFromWindow(v: View) {
                isAttached = false
            }
        })

        mainProgress = progressLayout!!.findViewById(R.id.pb_main_progress)
        mainProgress!!.indeterminateDrawable.setColorFilter(
            Color.WHITE,
            android.graphics.PorterDuff.Mode.MULTIPLY
        )

        mainLayout = progressLayout!!.findViewById(R.id.ll_main_layout)
        mainLayout!!.setBackgroundColor(Color.TRANSPARENT)

        layoutParams = WindowManager.LayoutParams(
            metrics.widthPixels, metrics.heightPixels,
            WindowManager.LayoutParams.TYPE_TOAST,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        layoutParams!!.gravity = Gravity.CENTER

        setConfiguration()
    }

    fun setConfiguration() {
        mainProgress!!.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(mContext, R.color.colorPrimary),
            android.graphics.PorterDuff.Mode.MULTIPLY
        )
        mainLayout!!.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent_white))
    }

    fun showProgress() {
        windowManager!!.addView(progressLayout, layoutParams)
    }

    fun hideProgress() {
        if (isAttached) {
            windowManager!!.removeViewImmediate(progressLayout)
        }
    }

    companion object {
        private var instance: ProgressWindow? = null

        fun getInstance(context: Context): ProgressWindow {
            synchronized(ProgressWindow::class.java) {
                if (instance == null) {
                    instance =
                        ProgressWindow(context)
                }
            }
            return instance!!
        }
    }


}