package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.AddressModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemAddressBinding
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.viewmodel.AddressViewModel

class AddressAdapter :
    ListAdapter<AddressModel, AddressAdapter.AddressViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class AddressViewHolder(
        val context: Context,
        val binding: ItemAddressBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AddressModel) = binding.apply {
            roadTxt.text = "${item.landNumber} ${item.road}"
            if (item.detailAddress != null) {
                buildingNameTxt.text = item.detailAddress
            }
            addressLayout.setOnClickListener {
                listener.click(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder =
        AddressViewHolder(
            parent.context,
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClickListener
        )

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface OnItemClickListener {
        fun click(item: AddressModel)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AddressModel>() {
            override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}