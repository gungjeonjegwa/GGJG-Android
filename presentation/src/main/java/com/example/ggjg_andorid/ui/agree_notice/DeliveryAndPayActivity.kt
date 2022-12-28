package com.example.ggjg_andorid.ui.agree_notice

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityDeliveryAndPayBinding
import com.example.ggjg_andorid.ui.base.BaseActivity

class DeliveryAndPayActivity :
    BaseActivity<ActivityDeliveryAndPayBinding>(R.layout.activity_delivery_and_pay) {
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