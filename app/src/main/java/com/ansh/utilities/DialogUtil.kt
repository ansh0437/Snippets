package com.ansh.utilities

import android.app.Activity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ansh.R
import com.ansh.extensions.resToStr
import com.ansh.interfaces.DialogListener
import com.ansh.interfaces.OnDataCallback
import com.ansh.view.adapters.DialogListAdapter

object DialogUtil {

    fun alert(
        activity: Activity,
        title: String,
        message: String,
        positiveLabel: String,
        negativeLabel: String,
        dialogListener: DialogListener
    ) {
        showDialog(activity, title, message, positiveLabel, negativeLabel, dialogListener)
    }

    fun alert(activity: Activity, title: String, message: String, dialogListener: DialogListener) {
        showDialog(
            activity,
            title,
            message,
            R.string.ok.resToStr,
            R.string.cancel.resToStr,
            dialogListener
        )
    }

    fun itemSelection(
        activity: Activity,
        title: String,
        list: List<Any>,
        dataCallback: (data: Any) -> Unit
    ) {
        val view = View.inflate(activity, R.layout.layout_list_dialog, null)

        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(false)
        val alertDialog = builder.create()

        alertDialog.setView(view)

        view.findViewById<TextView>(R.id.tvTitle).text = title

        view.findViewById<ImageButton>(R.id.ibClose).setOnClickListener {
            alertDialog.dismiss()
        }

        val rv = view.findViewById<RecyclerView>(R.id.rvList)

        rv.layoutManager = LinearLayoutManager(activity)
        rv.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        rv.adapter = DialogListAdapter(list, OnDataCallback { obj ->
            dataCallback.invoke(obj)
            alertDialog.dismiss()
        })

        alertDialog.show()
    }

    private fun showDialog(
        activity: Activity,
        title: String,
        message: String,
        positiveLabel: String,
        negativeLabel: String,
        dialogListener: DialogListener
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(positiveLabel) { di, _ -> dialogListener.onPositive(di) }
        builder.setNegativeButton(negativeLabel) { di, _ -> dialogListener.onNegative(di) }
        builder.show()
    }
}