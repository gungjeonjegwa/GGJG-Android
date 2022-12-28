package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.domain.entity.basket.MyBasketEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemPayBinding
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.PayViewModel
import java.text.DecimalFormat

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
            costTxt.text = "${DecimalFormat("#,###").format((item.price + (item.extraMoney ?: 0)) * item.count)}원"
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
        fun click(item: String)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyBasketEntity>() {
            override fun areItemsTheSame(
                oldItem: MyBasketEntity,
                newItem: MyBasketEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MyBasketEntity,
                newItem: MyBasketEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}