package com.example.ggjg_andorid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.RecentSearchEntity
import com.example.ggjg_andorid.databinding.ItemRecentSearchBinding

class RecentSearchAdapter(val clickListener: (RecentSearchEntity) -> Unit) :
    ListAdapter<RecentSearchEntity, RecentSearchAdapter.RecentSearchViewHolder>(diffUtil) {
    class RecentSearchViewHolder(val binding: ItemRecentSearchBinding, val clickListener: (RecentSearchEntity) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentSearchEntity) {
            binding.recentSearch = item
            binding.deleteRecentSearch.setOnClickListener {
                clickListener(item)
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
            clickListener
        )
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.bind(currentList[position])
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