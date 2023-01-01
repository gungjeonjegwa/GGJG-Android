package com.example.ggjg_andorid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ItemStampBinding
import com.example.ggjg_andorid.viewmodel.MyPageViewModel

class StampAdapter :
    ListAdapter<String, StampAdapter.StampViewHolder>(
        diffUtil) {

    class StampViewHolder(
        val context: Context,
        val binding: ItemStampBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Int) = binding.apply {
            stampCntTxt.text = item.toString()
            if (item > MyPageViewModel.stamp) {
                if (item == 10) {
                    stampCntTxt.text = null
                    stampImg.setBackgroundResource(R.drawable.ic_stamp_last)
                }
            } else {
                if (item == 10) {
                    stampImg.setBackgroundResource(R.drawable.ic_stamp_last_complete)
                } else {
                    stampCntTxt.setTextColor(context.getColor(R.color.white))
                    stampImg.setBackgroundResource(R.drawable.ic_stamp_complete)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampViewHolder =
        StampViewHolder(
            parent.context,
            ItemStampBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: StampViewHolder, position: Int) {
        holder.bind(position + 1)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}