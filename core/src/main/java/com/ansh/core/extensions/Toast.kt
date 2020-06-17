package com.ansh.core.extensions

import com.ansh.core.utilities.ToastUtil

fun String.toast(isTop: Boolean = false) {
    ToastUtil.shortToast(this, isTop)
}
