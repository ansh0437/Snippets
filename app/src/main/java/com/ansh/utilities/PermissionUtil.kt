package com.ansh.utilities

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ansh.R
import com.ansh.constants.AppConstant
import com.ansh.enums.PermissionEnum
import com.ansh.extensions.resToStr
import com.ansh.interfaces.DialogListener
import com.ansh.interfaces.PermissionCallback
import java.util.*

class PermissionUtil(
    mContext: Context,
    private val mPermissionCallback: PermissionCallback
) {

    private val mActivity: Activity = mContext as Activity
    private var permissionList = ArrayList<String>()
    private var listPermissionsNeeded = ArrayList<String>()
    private var dialogContent = ""
    private var requestCode: Int = 0

    fun processPermission(permissions: ArrayList<String>, dialogContent: String, requestCode: Int) {
        this.permissionList = permissions
        this.dialogContent = dialogContent
        this.requestCode = requestCode
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, requestCode)) {
                mPermissionCallback.onPermissionResult(requestCode, PermissionEnum.GRANTED)
            }
        } else {
            mPermissionCallback.onPermissionResult(requestCode, PermissionEnum.GRANTED)
        }
    }

    private fun checkAndRequestPermissions(
        permissions: ArrayList<String>,
        request_code: Int
    ): Boolean {
        if (permissions.size > 0) {
            listPermissionsNeeded = ArrayList()
            for (i in permissions.indices) {
                val hasPermission = ContextCompat.checkSelfPermission(mActivity, permissions[i])
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions[i])
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    mActivity,
                    listPermissionsNeeded.toTypedArray(),
                    request_code
                )
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
            1 -> if (grantResults.isNotEmpty()) {
                val perms = HashMap<String, Int>()
                for (i in permissions.indices) {
                    perms[permissions[i]] = grantResults[i]
                }
                val pendingPermissions = ArrayList<String>()
                for (i in listPermissionsNeeded.indices) {
                    if (perms[listPermissionsNeeded[i]] != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                mActivity,
                                listPermissionsNeeded[i]
                            )
                        )
                            pendingPermissions.add(listPermissionsNeeded[i])
                        else {
                            settingsDialog()
                            return
                        }
                    }
                }
                if (pendingPermissions.size > 0) {
                    DialogUtil.alert(
                        mActivity,
                        R.string.alert.resToStr,
                        dialogContent,
                        R.string.ok.resToStr,
                        R.string.cancel.resToStr,
                        object : DialogListener {
                            override fun onPositive(dialogInterface: DialogInterface) {
                                processPermission(
                                    permissionList,
                                    dialogContent,
                                    this@PermissionUtil.requestCode
                                )
                                dialogInterface.dismiss()
                            }

                            override fun onNegative(dialogInterface: DialogInterface) {
                                mPermissionCallback.onPermissionResult(
                                    this@PermissionUtil.requestCode,
                                    if (permissionList.size == pendingPermissions.size) PermissionEnum.DENIED
                                    else PermissionEnum.PARTIALLY_GRANTED
                                )
                                dialogInterface.dismiss()
                            }
                        }
                    )
                } else {
                    mPermissionCallback.onPermissionResult(this.requestCode, PermissionEnum.GRANTED)
                }
            }
        }
    }

    private fun settingsDialog() {
        DialogUtil.alert(mActivity,
            R.string.alert.resToStr,
            R.string.need_permission_manual.resToStr,
            R.string.ok.resToStr,
            R.string.cancel.resToStr,
            object : DialogListener {
                override fun onPositive(dialogInterface: DialogInterface) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.parse("package:" + mActivity.packageName)
                    mActivity.startActivityForResult(intent, AppConstant.REQUEST_CODE_SETTINGS)
                    dialogInterface.dismiss()
                }

                override fun onNegative(dialogInterface: DialogInterface) {
                    mPermissionCallback.onPermissionResult(
                        requestCode,
                        PermissionEnum.NEVER_ASK_AGAIN
                    )
                    dialogInterface.dismiss()
                }
            }
        )
    }
}
