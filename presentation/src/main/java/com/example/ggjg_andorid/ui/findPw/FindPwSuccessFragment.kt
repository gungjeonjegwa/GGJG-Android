package com.example.ggjg_andorid.ui.findPw

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentFindPwSuccessBinding
import com.example.ggjg_andorid.ui.base.BaseFragment

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