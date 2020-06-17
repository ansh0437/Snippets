package com.ansh.core.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ansh.core.adapter.generic.GenericViewHolder

abstract class BaseAdapter<T, E : ViewDataBinding> :
    RecyclerView.Adapter<GenericViewHolder<E>>() {

    private var mItems = arrayListOf<T>()
    protected var dataListener: ((T) -> Unit)? = null
    protected var dataPositionListener: ((T, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenericViewHolder(getDataBinding(parent))

    override fun getItemCount() = mItems.size

    override fun onBindViewHolder(holder: GenericViewHolder<E>, position: Int) {
        onBind(holder.binding, mItems[position], position)
    }

    fun updateData(list: List<T>) {
        mItems.clear()
        mItems.addAll(list)
        notifyDataSetChanged()
    }

    fun addListener(listener: (T) -> Unit) {
        this.dataListener = listener
    }

    fun addListener(listener: (T, Int) -> Unit) {
        this.dataPositionListener = listener
    }

    protected abstract fun getDataBinding(parent: ViewGroup): E
    protected abstract fun onBind(binding: E, data: T, position: Int)
}