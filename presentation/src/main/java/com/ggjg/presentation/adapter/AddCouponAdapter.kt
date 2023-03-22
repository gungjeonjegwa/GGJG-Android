package com.ggjg.presentation.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ItemAddCouponBinding
import com.ggjg.presentation.utils.extension.setOnTextChanged
import com.ggjg.presentation.utils.keyboardShow
import com.ggjg.presentation.viewmodel.CouponViewModel

class AddCouponAdapter(val context: Activity, var couponList: List<String>) :
    RecyclerView.Adapter<AddCouponAdapter.AddCouponViewHolder>() {

    private lateinit var itemChangeListener: OnItemChangeListener

    class AddCouponViewHolder(
        val context: Activity,
        val binding: ItemAddCouponBinding,
        val listener: OnItemChangeListener,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, position: Int) = binding.apply {
            if (position != 0) {
                couponTxt.text = "${context.getString(R.string.coupon_code)} $position"
            }
            writeCoupon.setText(item)
            writeCoupon.apply {
                setOnTextChanged { p0, _, _, _ ->
                    if (p0.isNullOrBlank()) {
                        couponTxt.setTextColor(context.getColor(R.color.dark_gray))
                        writeCoupon.isActivated = false
                    } else {
                        couponTxt.setTextColor(context.getColor(R.color.real_black))
                        writeCoupon.isActivated = true
                    }
                    listener.changeText(p0.toString(), position)
                }
                setOnClickListener {
                    CouponViewModel.currentPosition = position
                    couponTxt.setTextColor(context.getColor(R.color.real_black))
                    writeCoupon.isActivated = true
                }
                setOnFocusChangeListener { _, b ->
                    if (!b && writeCoupon.text.isNullOrBlank()) {
                        couponTxt.setTextColor(context.getColor(R.color.dark_gray))
                        writeCoupon.isActivated = false
                    }
                }
            }
            if (position == CouponViewModel.currentPosition) {
                keyboardShow(context, writeCoupon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCouponViewHolder =
        AddCouponViewHolder(
            context,
            ItemAddCouponBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemChangeListener
        )

    override fun onBindViewHolder(holder: AddCouponViewHolder, position: Int) {
        holder.bind(couponList[position], position)
    }

    interface OnItemChangeListener {
        fun changeText(item: String, index: Int)
    }

    fun setItemOnChangeListener(onItemChangeListener: OnItemChangeListener) {
        this.itemChangeListener = onItemChangeListener
    }

    override fun getItemCount(): Int = couponList.size

    fun changeData(couponList: List<String>) {
        this.couponList = couponList
        notifyDataSetChanged()
    }
}