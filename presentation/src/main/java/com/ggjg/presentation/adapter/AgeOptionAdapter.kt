package com.ggjg.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ItemPayOptionBinding

class AgeOptionAdapter :
    ListAdapter<String, AgeOptionAdapter.AgeOptionViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class AgeOptionViewHolder(
        val context: Context,
        val binding: ItemPayOptionBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) = binding.apply {
            sizeTxt.text = item
            sizeOptionLayout.setBackgroundColor(context.getColor(R.color.transparent))
            sizeOptionLayout.setOnClickListener {
                listener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgeOptionViewHolder =
        AgeOptionViewHolder(
            parent.context,
            ItemPayOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: AgeOptionViewHolder, position: Int) {
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