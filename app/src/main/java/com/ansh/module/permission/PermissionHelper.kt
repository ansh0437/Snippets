package com.ansh.module.permission

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ansh.data.constants.REQUEST_CODE_SETTINGS
import com.ansh.module.dialog.DialogListener
import com.ansh.module.dialog.DialogUtil

class PermissionHelper {

    private var mActivity: Activity? = null
    private var mFragment: Fragment? = null
    private var mPermissionCallback: PermissionCallback? = null

    private var isActivity = true

    private var iRequestCode: Int = 0
    private var mPermission: String = ""

    constructor(mActivity: Activity, permissionCallback: PermissionCallback) {
        this.mActivity = mActivity
        this.mPermissionCallback = permissionCallback
    }

    constructor(mFragment: Fragment, permissionCallback: PermissionCallback) {
        this.mFragment = mFragment
        this.mActivity = mFragment.requireActivity()
        this.isActivity = false
        this.mPermissionCallback = permissionCallback
    }

    fun isPermissionGranted(permission: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            mActivity!!,
            permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun askPermission(requestCode: Int, permission: String) {
        this.iRequestCode = requestCode
        this.mPermission = permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isPermissionGranted(mPermission)) {
            requestPermission()
        } else {
            mPermissionCallback!!.onGranted(iRequestCode)
        }
    }

    private fun requestPermission() {
        if (isActivity) {
            ActivityCompat.requestPermissions(
                mActivity!!,
                arrayOf(mPermission),
                iRequestCode
            )
        } else {
            mFragment?.requestPermissions(arrayOf(mPermission), iRequestCode)
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == iRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionCallback!!.onGranted(iRequestCode)
            } else {
                mPermissionCallback!!.onDenied(
                    iRequestCode, if (isActivity)
                        !ActivityCompat.shouldShowRequestPermissionRationale(
                            mActivity!!,
                            mPermission
                        )
                    else
                        mFragment?.shouldShowRequestPermissionRationale(mPermission)?.not()
                            ?: false
                )
            }
        }
    }

    fun askAgainDialog(message: String) {
        DialogUtil.alert(mActivity!!,
            message = message,
            positiveListener = DialogListener {
                requestPermission()
                it.dismiss()
            }
        )
    }

    fun neverAskDialog() {
        DialogUtil.alert(mActivity!!,
            message = "Give permission manually from settings.",
            positiveListener = DialogListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + mActivity!!.packageName)
                mActivity!!.startActivityForResult(intent, REQUEST_CODE_SETTINGS)
                it.dismiss()
            }
        )
    }
}
