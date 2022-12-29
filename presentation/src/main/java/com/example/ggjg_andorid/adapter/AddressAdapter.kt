package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemAddressBinding
import com.example.ggjg_andorid.databinding.ItemPayOptionBinding
import com.example.ggjg_andorid.viewmodel.AddressViewModel

class AddressAdapter :
    ListAdapter<AddressViewModel.Address, AddressAdapter.AddressViewHolder>(
        diffUtil) {

    private lateinit var itemClickListener: OnItemClickListener

    class AddressViewHolder(
        val context: Context,
        val binding: ItemAddressBinding,
        val listener: OnItemClickListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AddressViewModel.Address) = binding.apply {
            roadTxt.text = "${item.landNumber} ${item.roadName}"
            if (item.buildingName != null) {
                buildingNameTxt.text = item.buildingName
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
        fun click(item: AddressViewModel.Address)
    }

    fun setItemOnClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<AddressViewModel.Address>() {
            override fun areItemsTheSame(
                oldItem: AddressViewModel.Address,
                newItem: AddressViewModel.Address,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AddressViewModel.Address,
                newItem: AddressViewModel.Address,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}