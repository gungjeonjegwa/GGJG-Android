package com.example.ggjg_andorid.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemImageBinding
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.databinding.ItemReviewImageBinding
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.OrderViewModel

class ReviewImageAdapter :
    ListAdapter<String, ReviewImageAdapter.ReviewImageViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class ReviewImageViewHolder(
        val context: Context,
        val binding: ItemReviewImageBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) = binding.apply {
            reviewImg.load("file://$item")
            reviewImg.setOnClickListener {
                listener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewImageViewHolder =
        ReviewImageViewHolder(
            parent.context,
            ItemReviewImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: ReviewImageViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun click(item: String)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
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