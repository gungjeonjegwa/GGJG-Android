package com.example.ggjg_andorid.ui.my_page

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.databinding.FragmentMyPagePrivacyBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel

class MyPagePrivacyFragment :
    BaseFragment<FragmentMyPagePrivacyBinding>(R.layout.fragment_my_page_privacy) {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BaseActivity<ActivityMainBinding>).setOnKeyBackPressedListener(object :
            BaseActivity.OnKeyBackPressedListener {
            override fun onBack() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@MyPagePrivacyFragment).commit()
            }
        })
    }

    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        super.onDetach()
    }

    override fun createView() {
        binding.myPagePrivacy = this
        mainViewModel.hiddenNav(true)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@MyPagePrivacyFragment).commit()
            }
        }
    }
}