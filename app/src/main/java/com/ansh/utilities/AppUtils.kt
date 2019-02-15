package com.ansh.utilities

import android.os.Environment
import com.ansh.CoreApp
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object AppUtils {

    fun copyAppDbToExternalStorage() {
        try {
            val backupFolderLocation = Environment.getExternalStorageDirectory().absolutePath + "/"
            val currentDB = CoreApp.appCtx.getDatabasePath("DATABASE_NAME")
            val backupDB = File(backupFolderLocation + "DATABASE_NAME_BACKUP")
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

}