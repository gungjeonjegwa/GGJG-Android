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
import com.example.domain.entity.BreadEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemBreadBinding
import java.text.DecimalFormat

class BreadListAdapter(val clickListener: (BreadEntity.Bread) -> Unit) :
    ListAdapter<BreadEntity.Bread, BreadListAdapter.BreadListViewHolder>(diffUtil) {
    class BreadListViewHolder(
        val context: Context,
        val binding: ItemBreadBinding,
        val clickListener: (BreadEntity.Bread) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var currentDeliveryView = 0
        fun bind(item: BreadEntity.Bread) {
            binding.bread = item
            val height = (context.resources.displayMetrics.heightPixels * 0.24).toInt()
            binding.breadImg.layoutParams =
                ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
            binding.breadImg.load(item.imgUrl) {
                transformations(RoundedCornersTransformation(0f))
            }
            binding.breadCostTxt.text = DecimalFormat("#,###").format(item.price)
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
            clickListener
        )

    override fun onBindViewHolder(holder: BreadListViewHolder, position: Int) {
        holder.bind(currentList[position])
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