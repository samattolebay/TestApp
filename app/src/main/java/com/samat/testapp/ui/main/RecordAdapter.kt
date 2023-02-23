package com.samat.testapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samat.testapp.R
import com.samat.testapp.data.model.Record

class RecordAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Record, RecordAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val id = view.findViewById<TextView>(R.id.item_id)
        private val name = view.findViewById<TextView>(R.id.item_name)

        fun bind(record: Record, onItemClick: (id: Int) -> Unit) {
            id.text = view.context.getString(R.string.name, record.name)
            name.text = view.context.getString(R.string.id, record.name)
            view.setOnClickListener {
                val id = record.id
                id?.let { curId -> onItemClick(curId) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Record>() {
    override fun areItemsTheSame(oldItem: Record, newItem: Record) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Record, newItem: Record) = oldItem == newItem
}
