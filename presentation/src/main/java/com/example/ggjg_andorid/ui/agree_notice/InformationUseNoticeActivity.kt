package com.example.ggjg_andorid.ui.agree_notice

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityInformationUseNoticeBinding
import com.example.ggjg_andorid.ui.base.BaseActivity

class InformationUseNoticeActivity :
    BaseActivity<ActivityInformationUseNoticeBinding>(R.layout.activity_information_use_notice) {
    override fun createView() {
        binding.informationUseNotice = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                finish()
            }
        }
    }
}