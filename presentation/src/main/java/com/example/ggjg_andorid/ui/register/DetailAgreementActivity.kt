package com.example.ggjg_andorid.ui.register

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityAgreementDetailBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.viewmodel.RegisterViewModel

class DetailAgreementActivity :
    BaseActivity<ActivityAgreementDetailBinding>(R.layout.activity_agreement_detail) {
    override fun createView() {
        binding.agreementDetail = this
        initView()
    }

    private fun initView() = binding.run {
        agreeTitleTxt.text = RegisterViewModel.title
        agreeDetailTxt.text = RegisterViewModel.content
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                finish()
            }
        }
    }
}