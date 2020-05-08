package com.ansh.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ansh.adapter.BaseAdapter
import com.ansh.databinding.RowListItemBinding

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
