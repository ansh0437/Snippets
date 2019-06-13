package com.ansh.interfaces

import android.content.DialogInterface

interface DialogListener {
    fun onPositive(dialogInterface: DialogInterface)

    fun onNegative(dialogInterface: DialogInterface)
}
