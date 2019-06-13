package com.ansh.extensions

import androidx.databinding.BindingAdapter
import com.ansh.view.custom.CoreButton

@BindingAdapter("fill_color")
fun btnFillColor(view: CoreButton, color: Int) {
    view.setFillColor(color)
}
