package com.ansh.view.activities

import android.os.Bundle
import com.ansh.R
import com.ansh.interfaces.OnDataCallback
import com.ansh.utilities.DialogUtil
import com.ansh.view.activities.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideBar()

        DialogUtil.itemSelection(
            this,
            "",
            listOf(),
            OnDataCallback {

            }
        )

    }
}
