package com.example.vicetracker.AllVicesActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vicetracker.Model.Vice
import com.example.vicetracker.R

class ViceListAdapter(val viceClicked:(vice: Vice)->Unit):
ListAdapter<Vice, ViceListAdapter.ViceViewHolder>(VicesComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViceViewHolder {
        return ViceViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViceViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name,current.amount)
        holder.itemView.tag= current
        holder.itemView.setOnClickListener{
            viceClicked(holder.itemView.tag as Vice)
        }
    }

    class ViceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viceItemView: TextView = itemView.findViewById(R.id.itemText)
        private val quantityTextView: TextView = itemView.findViewById(R.id.quantityText)
        val buttonClicky: Button = itemView.findViewById(R.id.addUsage)

        fun bind(text: String?,quantity:Int?) {
            viceItemView.text = text
            quantityTextView.text = quantity.toString()
        }
        companion object {
            fun create(parent: ViewGroup): ViceViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ViceViewHolder(view)
            }
        }
        fun incrementUsage(){

        }
    }

    class VicesComparator : DiffUtil.ItemCallback<Vice>() {
        override fun areItemsTheSame(oldItem: Vice, newItem: Vice): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Vice, newItem: Vice): Boolean {
            return oldItem.name == newItem.name
        }
    }
}