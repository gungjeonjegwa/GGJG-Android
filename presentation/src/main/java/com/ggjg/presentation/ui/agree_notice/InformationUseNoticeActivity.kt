package com.ggjg.presentation.ui.agree_notice

import android.view.View
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityInformationUseNoticeBinding
import com.ggjg.presentation.ui.base.BaseActivity

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