package com.ansh.core.module.permission

interface PermissionCallback {

    fun onGranted(requestCode: Int)

    fun onDenied(requestCode: Int, neverAskAgain: Boolean)

}
