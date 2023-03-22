package com.ggjg.presentation.ui.findPw

import android.view.View
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentFindPwSuccessBinding
import com.ggjg.presentation.ui.base.BaseFragment

class FindPwSuccessFragment :
    BaseFragment<FragmentFindPwSuccessBinding>(R.layout.fragment_find_pw_success) {
    override fun onCreate() {
        
    }

    override fun createView() {
        binding.findPwSuccess = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn, R.id.loginBtn -> {
                requireActivity().finish()
            }
        }
    }
}