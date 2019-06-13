package com.ansh.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ansh.R
import com.ansh.interfaces.OnDataCallback
import com.ansh.interfaces.OnListItemClick

class DialogListAdapter(
    private val mList: List<Any>,
    private val onItemClick: OnDataCallback? = null
) :
    RecyclerView.Adapter<DialogListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        holder.nameTV.text = data.toString()
        holder.nameTV.setOnClickListener {
            onItemClick?.onData(data)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameTV: TextView = v.findViewById(R.id.tvName)
    }
}
