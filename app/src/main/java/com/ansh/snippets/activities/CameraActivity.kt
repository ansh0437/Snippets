package com.ansh.snippets.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.ansh.snippets.R

class CameraActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
    }

    fun selectPhoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Photo")
        builder.setItems(mutableListOf("Camera", "Gallery", "Cancel").toTypedArray()) { d, i ->
            when (i) {
                0 -> capturePhoto()
                1 -> galleryPhoto()
            }
            d.dismiss()
        }
        builder.show()
    }

    private fun capturePhoto() {

    }

    private fun galleryPhoto() {

    }
}
