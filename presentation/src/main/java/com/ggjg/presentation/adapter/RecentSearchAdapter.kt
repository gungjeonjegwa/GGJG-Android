package com.ggjg.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ggjg.domain.entity.bread.RecentSearchEntity
import com.ggjg.presentation.databinding.ItemRecentSearchBinding

class RecentSearchAdapter :
    ListAdapter<RecentSearchEntity, RecentSearchAdapter.RecentSearchViewHolder>(diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class RecentSearchViewHolder(
        val binding: ItemRecentSearchBinding,
        val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearchEntity) = binding.apply {
            recentSearch = item
            if (listener != null) {
                deleteRecentSearch.setOnClickListener {
                    listener.delete(item)
                }
                recentSearchTxt.setOnClickListener {
                    listener.search(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        return RecentSearchViewHolder(
            ItemRecentSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun delete(item: RecentSearchEntity)
        fun search(item: RecentSearchEntity)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecentSearchEntity>() {
            override fun areItemsTheSame(
                oldItem: RecentSearchEntity,
                newItem: RecentSearchEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RecentSearchEntity,
                newItem: RecentSearchEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}