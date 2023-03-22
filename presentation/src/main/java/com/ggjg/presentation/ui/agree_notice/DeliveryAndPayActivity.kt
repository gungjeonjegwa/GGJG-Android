package com.ggjg.presentation.ui.agree_notice

import android.view.View
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityDeliveryAndPayBinding
import com.ggjg.presentation.ui.base.BaseActivity

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