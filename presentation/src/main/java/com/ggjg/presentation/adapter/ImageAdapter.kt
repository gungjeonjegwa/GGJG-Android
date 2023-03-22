package com.ggjg.presentation.adapter

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ggjg.presentation.databinding.ItemImageBinding
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.OrderViewModel

class ImageAdapter :
    ListAdapter<String, ImageAdapter.ImageViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class ImageViewHolder(
        val context: Context,
        val binding: ItemImageBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) = binding.apply {
            Handler().postDelayed({
                imageLayout.layoutParams = imageLayout.layoutParams.apply {
                    height = imageLayout.width
                }
                image.load("file://$item")
            }, 1)
            selectedLayout.setVisible(OrderViewModel.imgList.contains(item))
            imageLayout.setOnClickListener {
                if (OrderViewModel.imgList.size < 2 || OrderViewModel.imgList.contains(item)) {
                    if (!OrderViewModel.imgList.contains(item)) {
                        selectedLayout.setVisible()
                    } else {
                        selectedLayout.setVisible(false)
                    }
                    listener.click(item)
                } else {
                    Toast.makeText(context, "사진은 최대 2장까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(
            parent.context,
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
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