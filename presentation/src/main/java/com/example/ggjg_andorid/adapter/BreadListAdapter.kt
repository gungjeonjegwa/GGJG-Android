package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.domain.entity.bread.BreadEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemBreadBinding
import com.example.ggjg_andorid.utils.heartChange
import java.text.DecimalFormat

class BreadListAdapter :
    ListAdapter<BreadEntity.Bread, BreadListAdapter.BreadListViewHolder>(diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class BreadListViewHolder(
        val context: Context,
        val binding: ItemBreadBinding,
        val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var currentDeliveryView = 0

        fun bind(item: BreadEntity.Bread) = binding.apply {
            bread = item
            val height = (context.resources.displayMetrics.heightPixels * 0.24).toInt()
            breadImg.layoutParams =
                ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
            breadImg.load(item.imgUrl) {
                transformations(RoundedCornersTransformation(0f))
            }
            if (item.price == null) {
                breadCostTxt.text = context.getString(R.string.sell_shop)
                wonTxt.visibility = View.GONE
            } else {
                breadCostTxt.text = DecimalFormat("#,###").format(item.price)
            }
            if (item.isSoldOut) {
                soldOutLayout.visibility = View.VISIBLE
            }
            likeBtn.setOnClickListener {
                it.heartChange(!it.isActivated, context)
                listener.like(item)
            }
            breadItemLayout.setOnClickListener {
                listener.detail(item)
            }
            item.sellDeliveryType.forEach {
                when (it.type) {
                    "GWANGJU" -> {
                        viewDeliveryType(R.drawable.bg_gwangju)
                    }
                    "BAEMIN" -> {
                        viewDeliveryType(R.drawable.bg_baemin)
                    }
                    else -> {
                        viewDeliveryType(R.drawable.bg_all_contry)
                    }
                }
            }
        }

        private fun viewDeliveryType(view: Int) {
            if (currentDeliveryView == 0) {
                binding.deliveryOption1.setBackgroundResource(view)
            } else {
                binding.deliveryOption2.setBackgroundResource(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadListViewHolder =
        BreadListViewHolder(
            parent.context,
            ItemBreadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: BreadListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun detail(item: BreadEntity.Bread)
        fun like(item: BreadEntity.Bread)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BreadEntity.Bread>() {
            override fun areItemsTheSame(
                oldItem: BreadEntity.Bread,
                newItem: BreadEntity.Bread
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: BreadEntity.Bread,
                newItem: BreadEntity.Bread
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}