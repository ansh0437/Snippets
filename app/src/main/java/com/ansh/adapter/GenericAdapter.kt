package com.ansh.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T : ViewDataBinding> : RecyclerView.Adapter<GenericViewHolder<T>>() {

    private val mItems = arrayListOf<GenericModel<T>>()

    fun updateData(list: List<GenericModel<T>>) {
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenericViewHolder(mItems[viewType].getDataBinding(parent))

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        mItems[position].onBind(holder.binding, position)
    }

}

interface GenericModel<T : ViewDataBinding> {
    fun getDataBinding(parent: ViewGroup): T

    fun onBind(binding: T, position: Int)
}

class GenericViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)