package com.ansh.interfaces

interface OnDoneListener {
    fun onComplete(obj: Any? = null)
    fun onError(obj: Any? = null)
}
