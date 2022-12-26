package com.example.ggjg_andorid.ui.detail

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityDetailDeliveryAndPayBinding
import com.example.ggjg_andorid.ui.base.BaseActivity

class DetailDeliveryAndPayActivity :
    BaseActivity<ActivityDetailDeliveryAndPayBinding>(R.layout.activity_detail_delivery_and_pay) {
    override fun createView() {
        binding.detailDeliveryAndPay = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                finish()
            }
        }
    }
}