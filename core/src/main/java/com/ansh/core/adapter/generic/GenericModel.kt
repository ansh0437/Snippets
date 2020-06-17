package com.ansh.core.adapter.generic

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

interface GenericModel<T : ViewDataBinding> {
    fun getDataBinding(parent: ViewGroup): T

    fun onBind(binding: T, position: Int)
}