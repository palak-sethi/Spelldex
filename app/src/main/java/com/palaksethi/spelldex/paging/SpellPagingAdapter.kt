package com.palaksethi.spelldex.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.palaksethi.spelldex.R
import com.palaksethi.spelldex.models.Data

class SpellPagingAdapter : PagingDataAdapter<Data, SpellPagingAdapter.SpellViewHolder>(COMPARATOR) {

    class SpellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spell = itemView.findViewById<TextView>(R.id.spell)
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.spell.text = item.attributes?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.spell_item_layout, parent, false)
        return SpellViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }

        }
    }
}