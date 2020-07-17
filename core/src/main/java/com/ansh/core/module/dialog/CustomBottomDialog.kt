package com.ansh.core.module.dialog

import android.content.Context
import android.view.LayoutInflater
import com.ansh.core.R
import com.ansh.core.adapter.generic.GenericAdapter
import com.ansh.core.adapter.models.BottomSheetModel
import com.ansh.core.databinding.BottomSheetBinding
import com.ansh.core.databinding.RowBottomSheetItemBinding
import com.ansh.core.extensions.resToStr
import com.google.android.material.bottomsheet.BottomSheetDialog

class CustomBottomDialog<T> private constructor(
    ctx: Context,
    private val list: List<T>,
    private val title: String = "",
    private val listener: ((T, BottomSheetDialog) -> Unit)
) : BottomSheetDialog(ctx) {

    companion object {
        fun <T> show(
            context: Context,
            list: List<T>,
            title: String = R.string.select.resToStr,
            listener: (T, BottomSheetDialog) -> Unit
        ) = CustomBottomDialog(context, list = list, title = title, listener = listener)
    }

    private var binding = BottomSheetBinding.inflate(LayoutInflater.from(ctx))

    private val genericAdapter = GenericAdapter<RowBottomSheetItemBinding>()

    init {
        setContentView(binding.root)
        binding.tvBottomSheetTitle.text = title
        initListeners()
        initAdapter()
        show()
    }

    private fun initListeners() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    /* Setup Adapter */

    private fun initAdapter() {
        genericAdapter.updateData(getModels())
        binding.recyclerView.adapter = genericAdapter
    }

    private fun getModels(): List<BottomSheetModel<T>> {
        return list.map { BottomSheetModel(it) { dto -> clickListener(dto) } }
    }

    private fun clickListener(it: T) {
        listener.invoke(it, this)
    }

}
