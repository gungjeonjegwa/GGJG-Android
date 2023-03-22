package com.ggjg.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.presentation.databinding.ItemPayBinding
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.utils.extension.toTotalMoney
import com.ggjg.presentation.viewmodel.PayViewModel
import java.text.DecimalFormat
import kotlin.math.roundToInt

class PayAdapter :
    ListAdapter<MyBasketEntity, PayAdapter.PayViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class PayViewHolder(
        val context: Context,
        val binding: ItemPayBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyBasketEntity, position: Int) = binding.apply {
            binding.myBasketEntity = item
            if (position + 1 == PayViewModel.shoppingList.size) {
                divideLine.setVisible(false)
            }
            breadImg.load(item.imgUrl)
            countTxt.text = "${item.count}개"
            if (item.unit != null) {
                optionTxt.text =
                    "-${if (item.age != null) "나이/${item.age}, " else ""}${item.unit}(${item.size})${
                        if (item.extraMoney != 0 && item.extraMoney != null) "(+${
                            DecimalFormat("#,###").format(item.extraMoney)
                        }원)" else ""
                    }"
            }
            val selectCoupon = PayViewModel.selectCouponList.find { it.id == position }
            if (selectCoupon != null) {
                cancelCouponBtn.setVisible()
                couponTxt.setVisible()
                selectCouponBtn.setVisible(false)
                val discount =
                    if (selectCoupon.type == "NORMAL") selectCoupon.price else (item.price.toTotalMoney(
                        item.extraMoney,
                        item.count) * (selectCoupon.price.toFloat() / 100)).roundToInt()
                couponTxt.text = "-${discount}원"
                costTxt.text =
                    "${
                        DecimalFormat("#,###").format(item.price.toTotalMoney(item.extraMoney,
                            item.count) - discount)
                    }원"
            } else {
                costTxt.text =
                    "${
                        DecimalFormat("#,###").format(item.price.toTotalMoney(item.extraMoney,
                            item.count))
                    }원"
            }
            selectCouponBtn.setOnClickListener {
                listener.click(item, position)
            }
            cancelCouponBtn.setOnClickListener {
                cancelCouponBtn.setVisible(false)
                couponTxt.setVisible(false)
                selectCouponBtn.setVisible()
                costTxt.text =
                    "${
                        DecimalFormat("#,###").format(item.price.toTotalMoney(item.extraMoney,
                            item.count))
                    }원"
                PayViewModel.selectCouponList =
                    PayViewModel.selectCouponList.filter { it.id != position }
                listener.cancel()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayViewHolder =
        PayViewHolder(
            parent.context,
            ItemPayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: PayViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    interface OnItemClickListener {
        fun click(item: MyBasketEntity, position: Int)
        fun cancel()
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyBasketEntity>() {
            override fun areItemsTheSame(
                oldItem: MyBasketEntity,
                newItem: MyBasketEntity,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MyBasketEntity,
                newItem: MyBasketEntity,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}