package com.palaksethi.spelldex.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.palaksethi.spelldex.R
import com.palaksethi.spelldex.models.Data

class SpellPagingAdapter : PagingDataAdapter<Data, SpellPagingAdapter.SpellViewHolder>(COMPARATOR) {
    var listener: SpellPagingAdapter.OnItemClickListener? = null
    var ctx : Context? = null
    class SpellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spell = itemView.findViewById<TextView>(R.id.spell)
        val pronunciation = itemView.findViewById<TextView>(R.id.pronunciation)
        val category = itemView.findViewById<TextView>(R.id.category)
        val effect = itemView.findViewById<TextView>(R.id.effect)
        val spellImage = itemView.findViewById<ImageView>(R.id.spellImage)
        val categoryLayout = itemView.findViewById<ConstraintLayout>(R.id.categoryLayout)
        val effectLayout = itemView.findViewById<ConstraintLayout>(R.id.effectLayout)
    }

    interface OnItemClickListener{
        fun onClicked(id:String)
    }
    fun setClickListener(listener1: SpellPagingAdapter.OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.spell.text = item.attributes?.name

            if(item.attributes?.incantation.equals(null)) {
                holder.pronunciation.visibility = View.INVISIBLE
            }
            holder.pronunciation.text = item.attributes?.incantation

            if(item.attributes?.category.equals(null)) {
                holder.categoryLayout.visibility = View.INVISIBLE
            }
            holder.category.text = item.attributes?.category

            if(item.attributes?.effect.equals(null)) {
                holder.effectLayout.visibility = View.INVISIBLE
            }
            holder.effect.text = item.attributes?.effect

            if(item.attributes?.image.equals(null) || item.attributes?.image.equals("https://potterdb.com/images/missing_spell.svg")) {
                holder.spellImage.setImageResource(R.drawable.missing_spell);
            } else {
                Glide.with(ctx!!).load(item.attributes?.image).thumbnail(0.05f)
                    .transition(DrawableTransitionOptions.withCrossFade()).into(holder.spellImage)
            }
        }

        holder.itemView.rootView.setOnClickListener {
            if (item != null) {
                listener!!.onClicked(item.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
        ctx = parent.context
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