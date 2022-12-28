package com.example.ggjg_andorid.ui.agree_notice

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityAgreementBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.viewmodel.AgreementViewModel

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