package com.ansh.module.permission

interface PermissionCallback {

    fun onGranted(requestCode: Int)

    fun onDenied(requestCode: Int, neverAskAgain: Boolean)

}
