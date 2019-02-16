package com.ansh.utilities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.ansh.R

object DialogUtils {

    fun alert(ctx: Context, title: String, message: String) {
        AlertDialog.Builder(ctx)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(message)
            .setPositiveButton(ctx.getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    fun permissionDialog(ctx: Context, title: String, msg: String) {
        AlertDialog.Builder(ctx)
            .setTitle(title)
            .setCancelable(false)
            .setMessage(msg)
            .setPositiveButton(ctx.getString(R.string.ok)) { _, _ ->
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + ctx.packageName)
                )
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                ctx.startActivity(intent)
            }
            .show()
    }
}