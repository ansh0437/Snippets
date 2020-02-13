package com.ansh.adapter.generic

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class GenericViewHolder<T : ViewDataBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root)