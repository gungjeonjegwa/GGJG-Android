package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding

class SizeOptionAdapter :
    ListAdapter<DetailBreadEntity.BreadSize, SizeOptionAdapter.SizeOptionViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class SizeOptionViewHolder(
        val context: Context,
        val binding: ItemPayOptionBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DetailBreadEntity.BreadSize) = binding.apply {
            sizeTxt.text =
                "${item.unit} ${item.size} ${if (item.extraMoney != null) "(${item.extraMoney})" else ""}"
            sizeOptionLayout.setOnClickListener {
                listener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeOptionViewHolder =
        SizeOptionViewHolder(
            parent.context,
            ItemPayOptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: SizeOptionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun click(item: DetailBreadEntity.BreadSize)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DetailBreadEntity.BreadSize>() {
            override fun areItemsTheSame(
                oldItem: DetailBreadEntity.BreadSize,
                newItem: DetailBreadEntity.BreadSize,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailBreadEntity.BreadSize,
                newItem: DetailBreadEntity.BreadSize,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}