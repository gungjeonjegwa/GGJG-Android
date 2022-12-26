package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemDetailBreadPayBinding
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.viewmodel.PayViewModel
import java.text.DecimalFormat

class DetailBreadPayAdapter :
    ListAdapter<PayViewModel.Bread, DetailBreadPayAdapter.DetailBreadPayViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class DetailBreadPayViewHolder(
        val context: Context,
        val binding: ItemDetailBreadPayBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PayViewModel.Bread) = binding.apply {
            var amount = item.amount
            nameTxt.text = item.name
            optionTxt.text = "-${item.unit} (${item.size}) ${
                if (item.extraMoney != 0) "(+${
                    DecimalFormat("#,###").format(item.extraMoney)
                }원)" else ""
            }"
            costTxt.text = "${(item.money + item.extraMoney) * amount}원"
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

        private fun changeView(amount: Int, item: PayViewModel.Bread) = binding.apply {
            costTxt.text = "${(item.money + item.extraMoney) * amount}원"
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
        fun plus(item: PayViewModel.Bread)
        fun minus(item: PayViewModel.Bread)
        fun delete(item: PayViewModel.Bread)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PayViewModel.Bread>() {
            override fun areItemsTheSame(
                oldItem: PayViewModel.Bread,
                newItem: PayViewModel.Bread,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PayViewModel.Bread,
                newItem: PayViewModel.Bread,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}