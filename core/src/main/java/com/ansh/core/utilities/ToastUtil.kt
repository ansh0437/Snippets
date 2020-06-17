package com.ansh.core.utilities

import android.view.Gravity
import android.widget.Toast

object ToastUtil {

    fun shortToast(msg: String, isTop: Boolean = false) {
        val toast = Toast.makeText(com.ansh.core.CoreApp.appCtx, msg, Toast.LENGTH_SHORT)
        if (isTop) toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.show()
    }

}
