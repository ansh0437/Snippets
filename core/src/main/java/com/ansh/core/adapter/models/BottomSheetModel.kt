package com.ansh.core.adapter.models

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ansh.core.adapter.generic.GenericModel
import com.ansh.core.databinding.RowBottomSheetItemBinding

class BottomSheetModel<T>(
    private val data: T,
    private val listener: (T) -> Unit
) : GenericModel<RowBottomSheetItemBinding> {

    override fun getDataBinding(parent: ViewGroup): RowBottomSheetItemBinding {
        return RowBottomSheetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBind(binding: RowBottomSheetItemBinding, position: Int) {
        binding.tvName.text = data.toString()
        binding.tvName.setOnClickListener { listener(data) }
    }

}
