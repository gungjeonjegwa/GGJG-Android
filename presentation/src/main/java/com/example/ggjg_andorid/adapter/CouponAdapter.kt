package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.coupon.CouponEntity
import com.example.ggjg_andorid.databinding.ItemCouponBinding
import com.example.ggjg_andorid.utils.toDate

class CouponAdapter :
    ListAdapter<CouponEntity, CouponAdapter.CouponViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class CouponViewHolder(
        val context: Context,
        val binding: ItemCouponBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CouponEntity) = binding.apply {
            discountAmount.text = item.price.toString()
            couponNameTxt.text = item.name
            expiredTxt.text = "${item.createdAt.toDate()} ~ ${item.finishedAt.toDate()} 까지"
            if (item.type == "NORMAL") {
                discountUnit.text = "원"
            } else {
                discountUnit.text = "%"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder =
        CouponViewHolder(
            parent.context,
            ItemCouponBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun click(item: String)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CouponEntity>() {
            override fun areItemsTheSame(oldItem: CouponEntity, newItem: CouponEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CouponEntity, newItem: CouponEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}