package com.palaksethi.spelldex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.palaksethi.spelldex.databinding.SpellItemLayoutBinding
import com.palaksethi.spelldex.models.Data
import com.palaksethi.spelldex.paging.SpellPagingAdapter

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    var listener: SearchAdapter.OnItemClickListener? = null
    var spells = mutableListOf<Data>()
    var ctx : Context? = null

    fun setSpellList(spells: List<Data>) {
        this.spells = spells.toMutableList()
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onClicked(id:String)
    }
    fun setClickListener(listener1: SearchAdapter.OnItemClickListener){
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        ctx = parent.context
        val binding = SpellItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = spells[position]
        if (item != null) {
            holder.binding.spell.text = item.attributes?.name

            if (item.attributes?.incantation.equals(null)) {
                holder.binding.pronunciation.visibility = View.INVISIBLE
            }
            holder.binding.pronunciation.text = item.attributes?.incantation

            if (item.attributes?.category.equals(null)) {
                holder.binding.categoryLayout.visibility = View.INVISIBLE
            }
            holder.binding.category.text = item.attributes?.category

            if (item.attributes?.effect.equals(null)) {
                holder.binding.effectLayout.visibility = View.INVISIBLE
            }
            holder.binding.effect.text = item.attributes?.effect

            if (item.attributes?.image.equals(null) || item.attributes?.image.equals("https://potterdb.com/images/missing_spell.svg")) {
                holder.binding.spellImage.setImageResource(R.drawable.missing_spell);
            } else {
                Glide.with(ctx!!).load(item.attributes?.image).thumbnail(0.05f)
                    .transition(DrawableTransitionOptions.withCrossFade()).into(holder.binding.spellImage)
            }

        }
        holder.itemView.rootView.setOnClickListener {
            if (item != null) {
                listener!!.onClicked(item.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return spells.size
    }


    class SearchViewHolder(val binding: SpellItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}