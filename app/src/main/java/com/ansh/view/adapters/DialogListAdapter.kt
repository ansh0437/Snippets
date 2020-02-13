package com.ansh.view.adapters

import com.ansh.R
import com.ansh.adapter.BaseAdapter
import com.ansh.databinding.RowListItemBinding

class DialogListAdapter : BaseAdapter<Any, RowListItemBinding>() {

    override fun getLayoutId() = R.layout.row_list_item

    override fun onBind(binding: RowListItemBinding, data: Any, position: Int) {
        binding.tvName.text = data.toString()
        binding.tvName.setOnClickListener { dataListener?.invoke(data) }
    }

}
