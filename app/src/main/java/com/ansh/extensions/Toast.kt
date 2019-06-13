package com.ansh.extensions

import com.ansh.utilities.ToastUtil

fun String.toast(isTop: Boolean = false) {
    ToastUtil.shortToast(this, isTop)
}
