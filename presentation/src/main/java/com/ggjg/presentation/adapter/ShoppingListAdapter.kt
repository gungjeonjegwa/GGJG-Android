package com.ggjg.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ItemShoppingListBinding
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.ShoppingListViewModel
import java.text.DecimalFormat

class ShoppingListAdapter :
    ListAdapter<MyBasketEntity, ShoppingListAdapter.ShoppingListViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class ShoppingListViewHolder(
        val context: Context,
        val binding: ItemShoppingListBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyBasketEntity, isLast: Boolean) = binding.apply {
            var amount = item.count
            nameTxt.text = item.title
            breadImg.load(item.imgUrl)
            costTxt.text = "${(item.price + (item.extraMoney ?: 0)) * amount}원"
            amountTxt.text = amount.toString()
            itemCheckBtn.isActivated = false
            ShoppingListViewModel.selectBreadList.forEach {
                if (it.id == item.id) {
                    itemCheckBtn.isActivated = true
                }
            }
            deleteBtn.setOnClickListener {
                listener.delete(item)
            }
            itemCheckBtn.setOnClickListener {
                itemCheckBtn.isActivated = !itemCheckBtn.isActivated
                listener.check(item, itemCheckBtn.isActivated)
            }
            optionTxt.isSelected = true
            if (isLast) {
                divideLine.setVisible(false)
            }
            if (item.unit != null) {
                optionTxt.text =
                    "-${if (item.age != null) "나이/${item.age}, " else ""}${item.unit}(${item.size})${
                        if (item.extraMoney != 0 && item.extraMoney != null) "(+${
                            DecimalFormat("#,###").format(item.extraMoney)
                        }원)" else ""
                    }"
            }
            if (!item.isSoldOut) {
                plusBtn.setOnClickListener {
                    var allAmount = 0
                    ShoppingListViewModel.selectBreadList.forEach {
                        if (it.breadId == item.breadId)
                            allAmount += it.count
                    }
                    if (allAmount < item.remainCount) {
                        amount++
                        changeView(amount, item)
                        listener.plus(item)
                    }
                }
                minusBtn.setOnClickListener {
                    if (amount != 1) {
                        amount--
                        changeView(amount, item)
                        listener.minus(item)
                    }
                }
            } else {
                listOf(nameTxt, optionTxt, costTxt, minusBtn, amountTxt, plusBtn).forEach {
                    it.setTextColor(context.getColor(R.color.gray))
                }
                soldOutLayout.setVisible()
            }
        }

        private fun changeView(amount: Int, item: MyBasketEntity) = binding.apply {
            costTxt.text = "${(item.price + (item.extraMoney ?: 0)) * amount}원"
            amountTxt.text = amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder =
        ShoppingListViewHolder(
            parent.context,
            ItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(currentList[position], currentList.size == position + 1)
    }

    interface OnItemClickListener {
        fun plus(item: MyBasketEntity)
        fun minus(item: MyBasketEntity)
        fun delete(item: MyBasketEntity)
        fun check(item: MyBasketEntity, isCheck: Boolean)
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