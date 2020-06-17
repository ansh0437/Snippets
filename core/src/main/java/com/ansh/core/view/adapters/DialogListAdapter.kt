package com.ansh.core.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ansh.core.adapter.BaseAdapter
import com.ansh.core.databinding.RowListItemBinding

class DialogListAdapter : BaseAdapter<Any, RowListItemBinding>() {

    override fun getDataBinding(parent: ViewGroup): RowListItemBinding =
        RowListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    override fun onBind(binding: RowListItemBinding, data: Any, position: Int) {
        binding.tvName.text = data.toString()
        binding.tvName.setOnClickListener { dataListener?.invoke(data) }
    }

}
