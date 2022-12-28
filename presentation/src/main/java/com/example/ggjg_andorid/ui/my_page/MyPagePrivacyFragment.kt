package com.example.ggjg_andorid.ui.my_page

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.databinding.FragmentMyPagePrivacyBinding
import com.example.ggjg_andorid.ui.agree_notice.AgreementActivity
import com.example.ggjg_andorid.ui.agree_notice.InformationUseNoticeActivity
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.AgreementViewModel
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
        (activity as BaseActivity<ActivityMainBinding>).deleteOnKeyBackPressedListener()
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
            R.id.termsOfServicesBtn -> {
                AgreementViewModel.apply {
                    title = getString(R.string.terms_of_services_no_arrow)
                    content = getString(R.string.terms_of_services_explain)
                }
                requireActivity().startActivity(Intent(context, AgreementActivity::class.java))
            }
            R.id.privacyHandleAgreeBtn -> {
                AgreementViewModel.apply {
                    title = getString(R.string.privacy_handle_agree_no_arrow)
                    content = getString(R.string.privacy_handle_agree_explain)
                }
                requireActivity().startActivity(Intent(context, AgreementActivity::class.java))
            }
            R.id.informationUseBtn -> {
                requireActivity().startActivity(Intent(context, InformationUseNoticeActivity::class.java))
            }
        }
    }
}