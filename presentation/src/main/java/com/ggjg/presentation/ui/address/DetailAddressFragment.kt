package com.ggjg.presentation.ui.address

import android.view.View
import androidx.navigation.findNavController
import com.ggjg.domain.model.AddressModel
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentAddressDetailBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.viewmodel.AddressViewModel
import com.ggjg.presentation.viewmodel.PayViewModel

class DetailAddressFragment :
    BaseFragment<FragmentAddressDetailBinding>(R.layout.fragment_address_detail) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.detailAddress = this
        initView()
    }

    private fun initView() = binding.apply {
        currentOrderAddressTxt.text =
            "${AddressViewModel.currentAddress!!.landNumber} ${AddressViewModel.currentAddress!!.road} (${AddressViewModel.currentAddress!!.zipcode})"
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.okayBtn -> {
                if (AddressViewModel.isPayment) {
                    PayViewModel.address = AddressModel(
                        AddressViewModel.currentAddress!!.zipcode,
                        AddressViewModel.currentAddress!!.road,
                        AddressViewModel.currentAddress!!.landNumber,
                        binding.writeDetailOrderAddress.text.toString(),
                        PayViewModel.defaultAddress == null
                    )
                    requireActivity().findNavController(R.id.mainContainer)
                        .popBackStack(R.id.payFragment, false)
                } else {
                    PayViewModel.address = AddressModel(
                        AddressViewModel.currentAddress!!.zipcode,
                        AddressViewModel.currentAddress!!.road,
                        AddressViewModel.currentAddress!!.landNumber,
                        binding.writeDetailOrderAddress.text.toString(),
                        true
                    )
                    requireActivity().findNavController(R.id.mainContainer)
                        .popBackStack(R.id.myPagePrivacyFragment, false)
                }
            }
        }
    }
}