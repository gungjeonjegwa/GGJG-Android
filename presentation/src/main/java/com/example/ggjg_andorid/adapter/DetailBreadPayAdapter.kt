package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.param.basket.MakeBasketParam
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemDetailBreadPayBinding
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.viewmodel.PayViewModel
import java.text.DecimalFormat

class DetailBreadPayAdapter :
    ListAdapter<MakeBasketParam, DetailBreadPayAdapter.DetailBreadPayViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class DetailBreadPayViewHolder(
        val context: Context,
        val binding: ItemDetailBreadPayBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MakeBasketParam) = binding.apply {
            var amount = item.count
            nameTxt.text = PayViewModel.breadData!!.name
            optionTxt.text = "-${if (item.age != null) "나이/${item.age}, " else ""}${item.unit}(${item.size})${
                if (item.extraMoney != 0 && item.extraMoney != null) "(+${
                    DecimalFormat("#,###").format(item.extraMoney)
                }원)" else ""
            }"
            costTxt.text = "${
                (PayViewModel.breadData!!.price.replace(",", "")
                    .toInt() + (item.extraMoney ?: 0)) * amount
            }원"
            amountTxt.text = amount.toString()
            deleteBtn.setOnClickListener {
                listener.delete(item)
            }
            plusBtn.setOnClickListener {
                amount++
                changeView(amount, item)
                listener.plus(item)
            }
            minusBtn.setOnClickListener {
                if (amount != 1) {
                    amount--
                    changeView(amount, item)
                    listener.minus(item)
                }
            }
        }

        private fun changeView(amount: Int, item: MakeBasketParam) = binding.apply {
            costTxt.text = "${
                (PayViewModel.breadData!!.price.replace(",", "")
                    .toInt() + (item.extraMoney ?: 0)) * amount
            }원"
            amountTxt.text = amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailBreadPayViewHolder =
        DetailBreadPayViewHolder(
            parent.context,
            ItemDetailBreadPayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: DetailBreadPayViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun plus(item: MakeBasketParam)
        fun minus(item: MakeBasketParam)
        fun delete(item: MakeBasketParam)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MakeBasketParam>() {
            override fun areItemsTheSame(
                oldItem: MakeBasketParam,
                newItem: MakeBasketParam,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MakeBasketParam,
                newItem: MakeBasketParam,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}