package com.example.ggjg_andorid.ui.address

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.AddressModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.AddressAdapter
import com.example.ggjg_andorid.databinding.FragmentAddressChangeBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.AddressViewModel
import com.example.ggjg_andorid.viewmodel.PayViewModel

class ChangeAddressFragment :
    BaseFragment<FragmentAddressChangeBinding>(R.layout.fragment_address_change) {
    private val addressViewModel by activityViewModels<AddressViewModel>()
    private lateinit var addressAdapter: AddressAdapter
    override fun createView() {
        binding.changeAddress = this
        addressViewModel.recentSearch()
        initView()
        repeatOnStart {
            addressViewModel.changeEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: AddressViewModel.ChangeEvent) = when (event) {
        is AddressViewModel.ChangeEvent.RecentSearch -> {
            binding.noRecentAddress.setVisible(false)
            addressAdapter.submitList(event.data)
        }
        is AddressViewModel.ChangeEvent.NoSearch -> {
            binding.noRecentAddress.setVisible()
        }
    }

    private fun initView() = binding.apply {
        addressAdapter = AddressAdapter().apply {
            setItemOnClickListener(object : AddressAdapter.OnItemClickListener {
                override fun click(item: AddressModel) {
                    PayViewModel.address = item
                    requireActivity().findNavController(R.id.mainContainer).popBackStack()
                }
            })
        }
        recentAddressList.apply {
            adapter = addressAdapter
            layoutManager = LinearLayoutManager(context)
        }
        orderAddressTxt.text =
            "${PayViewModel.defaultAddress!!.landNumber} ${PayViewModel.defaultAddress!!.road} (${PayViewModel.defaultAddress!!.zipcode})"
        if (!PayViewModel.defaultAddress!!.detailAddress.isNullOrBlank()) {
            deliveryOrderAddressTxt.text = "상세주소 : ${PayViewModel.defaultAddress!!.detailAddress}"
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.searchBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_changeAddressFragment_to_searchAddressFragment)
            }
        }
    }
}