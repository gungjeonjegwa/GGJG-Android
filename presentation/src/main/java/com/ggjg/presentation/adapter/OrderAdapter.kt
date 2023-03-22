package com.ggjg.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ggjg.domain.entity.order.MyOrderListEntity
import com.ggjg.presentation.databinding.ItemOrderBinding
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.utils.extension.toDeliveryType
import com.ggjg.presentation.utils.extension.toOrderDate
import java.text.DecimalFormat

class OrderAdapter :
    ListAdapter<MyOrderListEntity, OrderAdapter.OrderViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class OrderViewHolder(
        val context: Context,
        val binding: ItemOrderBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyOrderListEntity, isLast: Boolean) = binding.apply {
            breadImg.load(item.breadImg)
            deliveryTypeTxt.text =
                "${item.deliveryStatus.toDeliveryType()} • ${item.createdDate.toOrderDate()}"
            breadNameTxt.text = item.title
            costTxt.text = "${DecimalFormat("#,###").format(item.price)}원"
            if (isLast) {
                divide.visibility = View.INVISIBLE
            }
            when(item.deliveryStatus) {
                "WAITORDER" -> cancelBtn.setVisible()
                "DELIVERYING" -> cancelBtn.setVisible()
                "COMPLETEDELIVERY" -> recallAndReviewLayout.setVisible()
                "WAITCANCEL" -> cancelWaitingBtn.setVisible()
                "CANCEL" -> cancelFinishBtn.setVisible()
                "WAITRETURN" -> recallWaitingBtn.setVisible()
                "RETURN" -> recallFinishBtn.setVisible()
            }
            moveDetailBtn.setOnClickListener {
                listener.detail(item)
            }
            cancelBtn.setOnClickListener {
                listener.cancel(item)
            }
            recallBtn.setOnClickListener {
                listener.recall(item)
            }
            reviewBtn.setOnClickListener {
                listener.review(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder =
        OrderViewHolder(
            parent.context,
            ItemOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(currentList[position], currentList.size == position + 1)
    }

    interface OnItemClickListener {
        fun detail(item: MyOrderListEntity)
        fun cancel(item: MyOrderListEntity)
        fun recall(item: MyOrderListEntity)
        fun review(item: MyOrderListEntity)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyOrderListEntity>() {
            override fun areItemsTheSame(
                oldItem: MyOrderListEntity,
                newItem: MyOrderListEntity,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MyOrderListEntity,
                newItem: MyOrderListEntity,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}