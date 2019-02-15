package com.ansh.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.ansh.helpers.PermissionHelper

open class BaseActivity : AppCompatActivity() {

    private lateinit var permissionHelper: PermissionHelper

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        permissionHelper = PermissionHelper(this, mutableListOf<String>().toTypedArray(), 13)

        permissionHelper.hasPermission()

    }

}