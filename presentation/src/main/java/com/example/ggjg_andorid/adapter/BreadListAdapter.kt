package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.example.domain.model.BreadModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemBreadBinding
import com.example.ggjg_andorid.utils.heartChange
import com.example.ggjg_andorid.viewmodel.MainViewModel
import java.text.DecimalFormat

class BreadListAdapter :
    ListAdapter<BreadModel, BreadListAdapter.BreadListViewHolder>(diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class BreadListViewHolder(
        val context: Context,
        val binding: ItemBreadBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var currentDeliveryView = 0

        fun bind(item: BreadModel) = binding.apply {
            bread = item
            val breadImgParams = breadImg.layoutParams
            breadImgParams.height = (context.resources.displayMetrics.heightPixels * 0.22).toInt()
            breadImg.layoutParams = breadImgParams
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
            likeBtn.isActivated = item.isLike
            likeBtn.setOnClickListener {
                if (MainViewModel.isLogin) {
                    it.heartChange(!it.isActivated, context)
                    listener.like(item)
                }
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
        fun detail(item: BreadModel)
        fun like(item: BreadModel)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BreadModel>() {
            override fun areItemsTheSame(oldItem: BreadModel, newItem: BreadModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BreadModel, newItem: BreadModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}