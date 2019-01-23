package com.ansh.snippets.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.ansh.snippets.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val titles = mutableListOf("Camera")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles)
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
