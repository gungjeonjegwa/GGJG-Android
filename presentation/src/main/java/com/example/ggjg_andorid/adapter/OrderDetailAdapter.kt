package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.order.DetailOrderEntity
import com.example.ggjg_andorid.databinding.ItemOrderDetailBinding
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.PayViewModel
import java.text.DecimalFormat

class OrderDetailAdapter :
    ListAdapter<DetailOrderEntity.BuyItem, OrderDetailAdapter.PayViewHolder>(
        diffUtil) {

    class PayViewHolder(
        val context: Context,
        val binding: ItemOrderDetailBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DetailOrderEntity.BuyItem, position: Int) = binding.apply {
            binding.buyItem = item
            if (position + 1 == PayViewModel.shoppingList.size) {
                divideLine.setVisible(false)
            }
            costTxt.text =
                "${DecimalFormat("#,###").format((item.price + (item.extraMoney ?: 0)) * item.count)}원"
            countTxt.text = "${item.count}개"
            if (item.unit != null) {
                optionTxt.text =
                    "-${if (item.age != null) "나이/${item.age}, " else ""}${item.unit}(${item.size})${
                        if (item.extraMoney != 0 && item.extraMoney != null) "(+${
                            DecimalFormat("#,###").format(item.extraMoney)
                        }원)" else ""
                    }"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayViewHolder =
        PayViewHolder(
            parent.context,
            ItemOrderDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PayViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DetailOrderEntity.BuyItem>() {
            override fun areItemsTheSame(
                oldItem: DetailOrderEntity.BuyItem,
                newItem: DetailOrderEntity.BuyItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DetailOrderEntity.BuyItem,
                newItem: DetailOrderEntity.BuyItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}