package com.ggjg.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ggjg.domain.entity.order.DetailOrderEntity
import com.ggjg.presentation.databinding.ItemOrderDetailBinding
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.PayViewModel
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
            buyItem = item
            if (position + 1 == PayViewModel.shoppingList.size) {
                divideLine.setVisible(false)
            }
            breadImg.load(item.breadImg)
            costTxt.text =
                "${DecimalFormat("#,###").format(if (item.discountPrice != null) item.discountPrice else item.price)}원"
            countTxt.text = "${item.count}개"
            if (item.discountPrice != null) {
                coupon.setVisible()
                couponTxt.setVisible()
                couponTxt.text = "-${item.price - item.discountPrice!!}원"
            }
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