package com.example.ggjg_andorid.ui.address

import android.view.View
import androidx.navigation.findNavController
import com.example.domain.entity.order.InitOrderEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentAddressDetailBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.AddressViewModel
import com.example.ggjg_andorid.viewmodel.PayViewModel

class DetailAddressFragment :
    BaseFragment<FragmentAddressDetailBinding>(R.layout.fragment_address_detail) {
    override fun createView() {
        binding.detailAddress = this
        initView()
    }

    private fun initView() = binding.apply {
        currentOrderAddressTxt.text =
            "${AddressViewModel.currentAddress!!.landNumber} ${AddressViewModel.currentAddress!!.roadName} (${AddressViewModel.currentAddress!!.zipCode})"
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.okayBtn -> {
                PayViewModel.address = InitOrderEntity.Address(
                    AddressViewModel.currentAddress!!.zipCode,
                    AddressViewModel.currentAddress!!.roadName,
                    AddressViewModel.currentAddress!!.landNumber,
                    binding.writeDetailOrderAddress.text.toString(),
                    PayViewModel.defaultAddress == null
                )
                if (AddressViewModel.isPayment) {
                    requireActivity().findNavController(R.id.mainContainer)
                        .popBackStack(R.id.payFragment, false)
                } else {
                    requireActivity().findNavController(R.id.mainContainer)
                        .popBackStack(R.id.myPagePrivacyFragment, false)
                }
            }
        }
    }
}