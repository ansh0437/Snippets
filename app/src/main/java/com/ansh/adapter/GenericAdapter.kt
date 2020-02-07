package com.ansh.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class GenericViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

interface GenericModel<T : ViewDataBinding> {
    fun getDataBinding(parent: ViewGroup): T

    fun onBind(binding: T, position: Int)
}

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

/* GENERIC ABSTRACT ADAPTER */

//abstract class GenericAdapter<T, E : ViewDataBinding>(
//    private val layoutId: Int,
//    private val mItems: List<T>
//) : RecyclerView.Adapter<GenericViewHolder<E>>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenericViewHolder<E>(
//        DataBindingUtil.inflate(
//            LayoutInflater.from(parent.context), layoutId, parent, false
//        )
//    )
//
//    override fun getItemCount() = mItems.size
//
//    override fun onBindViewHolder(holder: GenericViewHolder<E>, position: Int) {
//        onBind(holder.binding, mItems[position])
//    }
//
//    abstract fun onBind(binding: E, data: T)
//}