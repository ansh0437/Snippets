package com.ansh.utilities

import android.app.Activity
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.ansh.R
import kotlinx.android.synthetic.main.layout_progress_dialog.view.*

object ProgressDialogUtil {

    private var alertDialog: AlertDialog? = null

    private fun createDialog(activity: Activity, message: String?) {
        val view = View.inflate(activity, R.layout.layout_progress_dialog, null)
        if (message != null) view.tvMessage.text = message
        alertDialog = AlertDialog.Builder(activity).setView(view).setCancelable(false).create()
    }

    fun show(activity: Activity, message: String? = null) {
        if (alertDialog == null) {
            createDialog(activity, message)
        }
        alertDialog?.let {
            if (!it.isShowing && !activity.isFinishing) {
                it.show()
            }
        }
    }

    fun hide(delay: Long = 100) {
        Handler().postDelayed({
            alertDialog?.let {
                if (it.isShowing) it.dismiss()
                alertDialog = null
            }
        }, delay)
    }
}
