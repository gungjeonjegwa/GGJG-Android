package com.ggjg.presentation.ui.agree_notice

import android.view.View
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityAgreementBinding
import com.ggjg.presentation.ui.base.BaseActivity
import com.ggjg.presentation.viewmodel.AgreementViewModel

class AgreementActivity :
    BaseActivity<ActivityAgreementBinding>(R.layout.activity_agreement) {
    override fun createView() {
        binding.agreementDetail = this
        initView()
    }

    private fun initView() = binding.run {
        agreeTitleTxt.text = AgreementViewModel.title
        agreeDetailTxt.text = AgreementViewModel.content
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                finish()
            }
        }
    }
}