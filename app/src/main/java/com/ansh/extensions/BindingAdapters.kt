package com.ansh.extensions

import android.databinding.BindingAdapter
import com.ansh.views.CoreButton

@BindingAdapter("fill_color")
fun btnFillColor(view: CoreButton, color: Int) {
    view.setFillColor(color)
}
