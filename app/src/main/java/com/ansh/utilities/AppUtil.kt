package com.ansh.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.ansh.CoreApp
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

object AppUtil {

    fun copyAppDbToExternalStorage(databaseName: String, backupDatabaseName: String) {
        try {
            val backupFolderLocation = Environment.getExternalStorageDirectory().absolutePath + "/"
            val currentDB = CoreApp.appCtx.getDatabasePath(databaseName)
            val backupDB = File(backupFolderLocation + backupDatabaseName)
            if (currentDB.exists()) {
                val src = FileInputStream(currentDB).channel
                val dst = FileOutputStream(backupDB).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideKeyboard() {
        (Objects.requireNonNull(CoreApp.appCtx.getSystemService(Activity.INPUT_METHOD_SERVICE)) as InputMethodManager)
            .toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun newIntent(ctx: Context, cls: Class<*>): Intent {
        return Intent(ctx, cls)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    @SuppressLint("HardwareIds")
    fun deviceUniqueId(): String {
        val telMgr = CoreApp.appCtx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (
            ContextCompat.checkSelfPermission(
                CoreApp.appCtx,
                android.Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            telMgr.deviceId ?: Settings.Secure.getString(
                CoreApp.appCtx.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        } else {
            Settings.Secure.getString(CoreApp.appCtx.contentResolver, Settings.Secure.ANDROID_ID)
        }
    }
}