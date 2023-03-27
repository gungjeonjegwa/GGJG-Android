package com.ggjg.presentation.ui.my_page

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentMyPagePrivacyBinding
import com.ggjg.presentation.ui.agree_notice.AgreementActivity
import com.ggjg.presentation.ui.agree_notice.InformationUseNoticeActivity
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.ui.toast.GGJGToast
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.*

class MyPagePrivacyFragment :
    BaseFragment<FragmentMyPagePrivacyBinding>(R.layout.fragment_my_page_privacy) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val myPageViewModel by activityViewModels<MyPageViewModel>()

    override fun onAttach(context: Context) {
        PayViewModel.address = null
        super.onAttach(context)
    }

    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        super.onDetach()
    }

    override fun onCreate() {
        repeatOnStart {
            myPageViewModel.privacyEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            myPageViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.myPagePrivacy = this
        AddressViewModel.isPayment = false
        mainViewModel.hiddenNav(true)
        initView()
    }

    private fun handleEvent(event: MyPageViewModel.PrivacyEvent) = when (event) {
        is MyPageViewModel.PrivacyEvent.ProfileData -> {
            binding.apply {
                nameTxt.text = event.data.name
                idTxt.text = event.data.id
                emailTxt.text = event.data.email
                phoneTxt.text = if (event.data.phone == null) "없음" else event.data.phone
                if (event.data.address != null) {
                    PayViewModel.defaultAddress = event.data.address
                    changeAddressBtn.setVisible()
                    addressTxt.text =
                        "${event.data.address!!.landNumber} ${event.data.address!!.road} (${event.data.address!!.zipcode})"
                    if (!event.data.address!!.detailAddress.isNullOrBlank()) {
                        detailAddressTxt.text = "상세주소 : ${event.data.address!!.detailAddress}"
                    }
                } else {
                    setOrderAddressBtn.setVisible()
                }
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when(event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        myPageViewModel.profilePrivate()
        if (PayViewModel.address != null) {
            myPageViewModel.changeAddress()
            changeAddressBtn.setVisible()
            setOrderAddressBtn.setVisible(false)
            addressTxt.text =
                "${PayViewModel.address!!.landNumber} ${PayViewModel.address!!.road} (${PayViewModel.address!!.zipcode})"
            if (!PayViewModel.address!!.detailAddress.isNullOrBlank()) {
                detailAddressTxt.text = "상세주소 : ${PayViewModel.address!!.detailAddress}"
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.editBtn -> {
                GGJGToast.createToast(requireContext(), "지금은 지원되지 않는 기능입니다.", false)
//                requireActivity().findNavController(R.id.mainContainer)
//                    .navigate(R.id.action_myPagePrivacyFragment_to_myPageEditFragment)
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
                requireActivity().startActivity(
                    Intent(
                        context,
                        InformationUseNoticeActivity::class.java
                    )
                )
            }
            R.id.setOrderAddressBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_myPagePrivacyFragment_to_searchAddressFragment)
            }
            R.id.changeAddressBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_myPagePrivacyFragment_to_changeAddressFragment)
            }
        }
    }
}