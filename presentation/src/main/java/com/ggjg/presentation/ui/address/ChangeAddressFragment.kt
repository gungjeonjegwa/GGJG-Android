package com.ggjg.presentation.ui.address

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.model.AddressModel
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.AddressAdapter
import com.ggjg.presentation.databinding.FragmentAddressChangeBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.viewmodel.AddressViewModel
import com.ggjg.presentation.viewmodel.PayViewModel

class ChangeAddressFragment :
    BaseFragment<FragmentAddressChangeBinding>(R.layout.fragment_address_change) {
    private val addressViewModel by activityViewModels<AddressViewModel>()
    private lateinit var addressAdapter: AddressAdapter
    override fun onCreate() {
        repeatOnStart {
            addressViewModel.changeEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            addressViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        addressViewModel.recentSearch()
        binding.changeAddress = this
        initView()
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

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

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