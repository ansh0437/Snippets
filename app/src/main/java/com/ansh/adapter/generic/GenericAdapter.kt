package com.ansh.adapter.generic

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericAdapter<T : ViewDataBinding>(
    private val mItems: List<GenericModel<T>>
) : RecyclerView.Adapter<GenericViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> =
        GenericViewHolder(mItems[viewType].getDataBinding(parent))

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        mItems[position].onBind(holder.binding, position)
    }

}