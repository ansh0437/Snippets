package com.ansh.core.module.dialog

import android.app.Activity
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import com.ansh.core.R
import com.ansh.core.databinding.LayoutAlertDialogBinding
import com.ansh.core.databinding.LayoutInputDialogBinding
import com.ansh.core.databinding.LayoutListDialogBinding
import com.ansh.core.extensions.resToStr
import com.ansh.core.extensions.toast
import com.ansh.core.view.adapters.DialogListAdapter

object DialogUtil {

    fun confirmation(
        activity: Activity,
        message: String,
        positiveListener: ((DialogInterface) -> Unit)? = null,
        negativeListener: ((DialogInterface) -> Unit)? = null
    ) {
        alert(
            activity,
            R.string.confirmation.resToStr,
            message,
            R.string.yes.resToStr,
            R.string.cancel.resToStr,
            positiveListener,
            {
                if (negativeListener == null) it.dismiss()
                else negativeListener(it)
            }
        )
    }

    fun alert(
        activity: Activity,
        title: String = R.string.alert.resToStr,
        message: String,
        positiveLabel: String = R.string.ok.resToStr,
        negativeLabel: String = R.string.cancel.resToStr,
        positiveListener: ((DialogInterface) -> Unit)? = null,
        negativeListener: ((DialogInterface) -> Unit)? = null
    ) {
        val binding = LayoutAlertDialogBinding.inflate(LayoutInflater.from(activity))

        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        val alertDialog = builder.create()

        alertDialog.setView(binding.root)

        binding.tvTitle.text = title
        binding.tvMsg.text = message

        binding.tvLeft.apply {
            text = negativeLabel
//            visibility = if (negativeListener == null) View.GONE else View.VISIBLE
        }

        binding.tvRight.apply {
            text = positiveLabel
//            visibility = if (positiveListener == null) View.GONE else View.VISIBLE
        }

        val onClickListener: (listener: ((DialogInterface) -> Unit)?) -> Unit = {
            if (it == null) alertDialog.dismiss() else it(alertDialog)
        }

        binding.ibClose.setOnClickListener { onClickListener(null) }
        binding.tvLeft.setOnClickListener { onClickListener(negativeListener) }
        binding.tvRight.setOnClickListener { onClickListener(positiveListener) }

        alertDialog.show()
    }

    fun itemSelection(
        activity: Activity, title: String, list: List<Any>, dataCallback: (data: Any) -> Unit
    ) {
        val binding = LayoutListDialogBinding.inflate(LayoutInflater.from(activity))

        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        val alertDialog = builder.create()

        alertDialog.setView(binding.root)

        binding.tvTitle.text = title
        binding.ibClose.setOnClickListener { alertDialog.dismiss() }

        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvList.adapter = DialogListAdapter().apply {
            updateData(list)
            addListener { data ->
                dataCallback.invoke(data)
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

    fun inputDialog(
        activity: Activity,
        title: String,
        positiveLabel: String,
        negativeLabel: String,
        emptyErrorMessage: String = "Input field empty",
        negativeListener: DialogListener? = null,
        inputCallback: (data: String) -> Unit
    ) {
        val binding = LayoutInputDialogBinding.inflate(LayoutInflater.from(activity))

        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        val alertDialog = builder.create()

        alertDialog.setView(binding.root)

        binding.tvTitle.text = title
        binding.tvRight.text = positiveLabel
        binding.tvLeft.text = negativeLabel

        binding.ibClose.setOnClickListener {
            if (negativeListener == null) alertDialog.dismiss()
            else negativeListener.onClick(alertDialog)
        }

        binding.tvLeft.setOnClickListener {
            if (negativeListener == null) alertDialog.dismiss()
            else negativeListener.onClick(alertDialog)
        }

        binding.tvRight.setOnClickListener {
            val input = binding.etInput.text.toString().trim()
            if (input.isEmpty()) {
                emptyErrorMessage.toast()
            } else {
                inputCallback.invoke(input)
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }
}