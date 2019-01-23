package com.ansh.snippets.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.ansh.snippets.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val labels = mutableListOf(
        "Camera"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, labels)
        listView.setOnItemClickListener { _, _, pos, _ ->
            startActivity(
                when (pos) {
                    0 -> Intent(this, CameraActivity::class.java)
                    else -> null
                }
            )
        }
    }
}
