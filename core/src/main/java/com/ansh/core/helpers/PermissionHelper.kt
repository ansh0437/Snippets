package com.ansh.core.helpers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ansh.core.R
import com.ansh.core.data.constants.RC_SETTINGS
import com.ansh.core.data.enums.PermissionEnum
import com.ansh.core.extensions.resToStr
import com.ansh.core.interfaces.PermissionCallback
import com.ansh.core.module.dialog.DialogUtil
import java.util.*

class PermissionHelper private constructor(
    private val isActivity: Boolean,
    private val mActivity: Activity,
    private val mFragment: Fragment?,
    private val mPermissionCallback: PermissionCallback?
) {

    class Builder private constructor() {

        companion object {
            fun forActivity(activity: Activity) = Builder().setActivity(activity)

            fun forFragment(fragment: Fragment) = Builder().setFragment(fragment)
        }

        private var isActivity: Boolean = true
        private lateinit var mActivity: Activity
        private lateinit var mFragment: Fragment
        private var mPermissionCallback: PermissionCallback? = null

        private fun setActivity(activity: Activity) = apply {
            mActivity = activity
        }

        private fun setFragment(fragment: Fragment) = apply {
            isActivity = false
            mFragment = fragment
            mActivity = mFragment.requireActivity()
        }

        fun addCallback(permissionCallback: PermissionCallback) = apply {
            mPermissionCallback = permissionCallback
        }

        fun build(): PermissionHelper {
            return PermissionHelper(isActivity, mActivity, mFragment, mPermissionCallback)
        }
    }

    private var mPermissionList = ArrayList<String>()
    private var mPermissionsNeededList = ArrayList<String>()
    private var mDialogMessage = ""
    private var iRequestCode = 0

    fun hasPermission(permission: String) = ContextCompat
        .checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(requestCode: Int, permission: String, alertMessage: String) {
        iRequestCode = requestCode
        mPermissionList = arrayListOf(permission)
        mDialogMessage = alertMessage

        if (isActivity) {
            ActivityCompat.requestPermissions(
                mActivity,
                mPermissionList.toTypedArray(),
                iRequestCode
            )
        } else {
            mFragment!!.requestPermissions(mPermissionList.toTypedArray(), iRequestCode)
        }
    }

    fun processPermission(permissions: ArrayList<String>, dialogContent: String, requestCode: Int) {
        this.mPermissionList = permissions
        this.mDialogMessage = dialogContent
        this.iRequestCode = requestCode
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, requestCode)) {
                mPermissionCallback?.onPermissionResult(requestCode, PermissionEnum.Granted)
            }
        } else {
            mPermissionCallback?.onPermissionResult(requestCode, PermissionEnum.Granted)
        }
    }

    private fun checkAndRequestPermissions(
        permissions: ArrayList<String>,
        request_code: Int
    ): Boolean {
        if (permissions.size > 0) {
            mPermissionsNeededList = ArrayList()
            for (i in permissions.indices) {
                val hasPermission =
                    ContextCompat.checkSelfPermission(mActivity, permissions[i])
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    mPermissionsNeededList.add(permissions[i])
                }
            }
            if (mPermissionsNeededList.isNotEmpty()) {
                if (isActivity) {
                    ActivityCompat.requestPermissions(
                        mActivity,
                        mPermissionsNeededList.toTypedArray(),
                        request_code
                    )
                } else {
                    mFragment!!.requestPermissions(
                        mPermissionsNeededList.toTypedArray(),
                        request_code
                    )
                }
                return false
            }
        }
        return true
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            iRequestCode -> if (grantResults.isNotEmpty()) {
                val perms = HashMap<String, Int>()
                for (i in permissions.indices) {
                    perms[permissions[i]] = grantResults[i]
                }
                val pendingPermissions = ArrayList<String>()
                for (i in mPermissionsNeededList.indices) {
                    if (perms[mPermissionsNeededList[i]] != PackageManager.PERMISSION_GRANTED) {
                        val b: Boolean = if (isActivity) {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                mActivity,
                                mPermissionsNeededList[i]
                            )
                        } else {
                            mFragment!!.shouldShowRequestPermissionRationale(
                                mPermissionsNeededList[i]
                            )
                        }
                        if (b) {
                            pendingPermissions.add(mPermissionsNeededList[i])
                        } else {
                            settingsDialog()
                            return
                        }
                    }
                }
                if (pendingPermissions.size > 0) {
                    DialogUtil.alert(
                        mActivity,
                        R.string.alert.resToStr,
                        mDialogMessage,
                        R.string.ok.resToStr,
                        R.string.cancel.resToStr,
                        positiveListener = {
                            processPermission(
                                mPermissionList,
                                mDialogMessage,
                                this@PermissionHelper.iRequestCode
                            )
                            it.dismiss()
                        },
                        negativeListener = {
                            mPermissionCallback?.onPermissionResult(
                                this@PermissionHelper.iRequestCode,
                                if (mPermissionList.size == pendingPermissions.size) PermissionEnum.Denied
                                else PermissionEnum.PartiallyGranted
                            )
                            it.dismiss()
                        }
                    )
                } else {
                    mPermissionCallback?.onPermissionResult(
                        this.iRequestCode,
                        PermissionEnum.Granted
                    )
                }
            }
        }
    }

    private fun settingsDialog() {
        DialogUtil.alert(
            mActivity,
            R.string.alert.resToStr,
            R.string.need_permission_manual.resToStr,
            R.string.ok.resToStr,
            R.string.cancel.resToStr,
            positiveListener = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + mActivity.packageName)
                mActivity.startActivityForResult(intent, RC_SETTINGS)
                it.dismiss()
            },
            negativeListener = {
                mPermissionCallback?.onPermissionResult(
                    iRequestCode,
                    PermissionEnum.NeverAskAgain
                )
                it.dismiss()
            }
        )
    }
}
