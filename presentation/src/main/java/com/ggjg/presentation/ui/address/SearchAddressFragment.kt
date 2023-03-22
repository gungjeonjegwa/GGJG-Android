package com.ggjg.presentation.ui.address

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.model.AddressModel
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.AddressAdapter
import com.ggjg.presentation.databinding.FragmentAddressSearchBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.viewmodel.AddressViewModel
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchAddressFragment :
    BaseFragment<FragmentAddressSearchBinding>(R.layout.fragment_address_search) {
    private val addressViewModel by activityViewModels<AddressViewModel>()
    private lateinit var addressAdapter: AddressAdapter
    override fun onCreate() {
        repeatOnStart {
            addressViewModel.searchEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            addressViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.addressSearch = this
        initView()
    }

    private fun handleEvent(event: AddressViewModel.SearchEvent) = when (event) {
        is AddressViewModel.SearchEvent.AddressList -> {
            if (AddressViewModel.isClickSearch && event.data.isEmpty()) {
                binding.noSearchTxt.setVisible()
                AddressViewModel.isClickSearch = false
            } else {
                binding.noSearchTxt.setVisible(false)
                addressAdapter.submitList(event.data)
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        searchAddress.textChanges()
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { addressViewModel.search(it.toString()) }
        addressAdapter = AddressAdapter().apply {
            setItemOnClickListener(object : AddressAdapter.OnItemClickListener {
                override fun click(item: AddressModel) {
                    AddressViewModel.currentAddress = item
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_searchAddressFragment_to_detailAddressFragment)
                }
            })
        }
        addressList.apply {
            adapter = addressAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.searchBtn -> {
                AddressViewModel.isClickSearch = true
                addressViewModel.search(binding.searchAddress.text.toString())
            }
        }
    }
}