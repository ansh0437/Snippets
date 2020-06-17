package com.ansh.core.extensions

import androidx.databinding.BindingAdapter
import com.ansh.core.view.custom.CoreButton

@BindingAdapter("fill_color")
fun btnFillColor(view: CoreButton, color: Int) {
    view.setFillColor(color)
}
