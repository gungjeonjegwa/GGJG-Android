package com.ggjg.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ggjg.presentation.databinding.ItemBannerInfoBinding

class DetailBreadInfoImgAdapter :
    ListAdapter<String, DetailBreadInfoImgAdapter.DetailBreadInfoImgViewHolder>(diffUtil) {

    class DetailBreadInfoImgViewHolder(val binding: ItemBannerInfoBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = binding.apply {
            bannerImg.load(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DetailBreadInfoImgViewHolder =
        DetailBreadInfoImgViewHolder(
            ItemBannerInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )

    override fun onBindViewHolder(holder: DetailBreadInfoImgViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}