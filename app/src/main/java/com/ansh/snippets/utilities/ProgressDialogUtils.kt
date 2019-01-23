package com.ansh.snippets.utilities

import android.app.Activity
import android.app.ProgressDialog
import android.os.Handler
import com.ansh.snippets.R
import com.ansh.snippets.SnippetsApp

object ProgressDialogUtils {

    private var progressDialog: ProgressDialog? = null

    fun start(activity: Activity, msg: String) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity, ProgressDialog.STYLE_SPINNER)
        }
        if (!progressDialog!!.isShowing && !activity.isFinishing) {
            progressDialog = ProgressDialog.show(
                activity,
                SnippetsApp.appCtx.getString(R.string.app_name),
                msg,
                true,
                false
            )
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.setCancelable(false)
        }
    }

    fun stop() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
            }
            progressDialog = null
        }
    }

    fun stopDelay() {
        Handler().postDelayed({
            if (progressDialog != null) {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
                progressDialog = null
            }
        }, 1000)
    }
}
