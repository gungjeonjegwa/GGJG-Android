package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.bread.SearchEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.databinding.ItemSearchBinding

class SearchAdapter :
    ListAdapter<SearchEntity, SearchAdapter.SearchViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class SearchViewHolder(
        val context: Context,
        val binding: ItemSearchBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchEntity) = binding.apply {
            startTxt.text = item.title
            searchLayout.setOnClickListener {
                listener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            parent.context,
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun click(item: SearchEntity)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchEntity>() {
            override fun areItemsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}