package com.ansh.helpers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ansh.R
import com.ansh.constants.REQUEST_CODE_SETTINGS
import com.ansh.enums.PermissionEnum
import com.ansh.extensions.resToStr
import com.ansh.interfaces.NegativeListener
import com.ansh.interfaces.PermissionCallback
import com.ansh.interfaces.PositiveListener
import com.ansh.utilities.DialogUtil
import java.util.*

class PermissionHelper private constructor(private val builder: Builder) {

    class Builder {

        var isActivity: Boolean = true
        lateinit var mActivity: Activity
        lateinit var mFragment: Fragment
        lateinit var mPermissionCallback: PermissionCallback

        fun setActivity(activity: Activity) = apply {
            mActivity = activity
        }

        fun setFragment(fragment: Fragment) = apply {
            isActivity = false
            mFragment = fragment
            mActivity = mFragment.requireActivity()
        }

        fun addCallback(permissionCallback: PermissionCallback) = apply {
            mPermissionCallback = permissionCallback
        }

        fun build(): PermissionHelper = PermissionHelper(this)
    }

    private var mPermissionList = ArrayList<String>()
    private var mPermissionsNeededList = ArrayList<String>()
    private var mDialogMessage = ""
    private var iRequestCode: Int = 0

    fun processPermission(permissions: ArrayList<String>, dialogContent: String, requestCode: Int) {
        this.mPermissionList = permissions
        this.mDialogMessage = dialogContent
        this.iRequestCode = requestCode
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, requestCode)) {
                builder.mPermissionCallback.onPermissionResult(requestCode, PermissionEnum.GRANTED)
            }
        } else {
            builder.mPermissionCallback.onPermissionResult(requestCode, PermissionEnum.GRANTED)
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
                    ContextCompat.checkSelfPermission(builder.mActivity, permissions[i])
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    mPermissionsNeededList.add(permissions[i])
                }
            }
            if (mPermissionsNeededList.isNotEmpty()) {
                if (builder.isActivity) {
                    ActivityCompat.requestPermissions(
                        builder.mActivity,
                        mPermissionsNeededList.toTypedArray(),
                        request_code
                    )
                } else {
                    builder.mFragment.requestPermissions(
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
                        val b: Boolean = if (builder.isActivity) {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                builder.mActivity,
                                mPermissionsNeededList[i]
                            )
                        } else {
                            builder.mFragment.shouldShowRequestPermissionRationale(
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
                        builder.mActivity,
                        R.string.alert.resToStr,
                        mDialogMessage,
                        R.string.ok.resToStr,
                        R.string.cancel.resToStr,
                        PositiveListener {
                            processPermission(
                                mPermissionList,
                                mDialogMessage,
                                this@PermissionHelper.iRequestCode
                            )
                            it.dismiss()
                        },
                        NegativeListener {
                            builder.mPermissionCallback.onPermissionResult(
                                this@PermissionHelper.iRequestCode,
                                if (mPermissionList.size == pendingPermissions.size) PermissionEnum.DENIED
                                else PermissionEnum.PARTIALLY_GRANTED
                            )
                            it.dismiss()
                        }
                    )
                } else {
                    builder.mPermissionCallback.onPermissionResult(
                        this.iRequestCode,
                        PermissionEnum.GRANTED
                    )
                }
            }
        }
    }

    private fun settingsDialog() {
        DialogUtil.alert(
            builder.mActivity,
            R.string.alert.resToStr,
            R.string.need_permission_manual.resToStr,
            R.string.ok.resToStr,
            R.string.cancel.resToStr,
            PositiveListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + builder.mActivity.packageName)
                builder.mActivity.startActivityForResult(intent, REQUEST_CODE_SETTINGS)
                it.dismiss()
            },
            NegativeListener {
                builder.mPermissionCallback.onPermissionResult(
                    iRequestCode,
                    PermissionEnum.NEVER_ASK_AGAIN
                )
                it.dismiss()
            }
        )
    }
}
