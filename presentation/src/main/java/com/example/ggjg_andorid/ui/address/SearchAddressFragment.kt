package com.example.ggjg_andorid.ui.address

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.AddressAdapter
import com.example.ggjg_andorid.databinding.FragmentAddressSearchBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.AddressViewModel
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchAddressFragment :
    BaseFragment<FragmentAddressSearchBinding>(R.layout.fragment_address_search) {
    private val addressViewModel by activityViewModels<AddressViewModel>()
    private lateinit var addressAdapter: AddressAdapter

    override fun createView() {
        binding.addressSearch = this
        initView()
        repeatOnStart {
            addressViewModel.searchEventFlow.collect { event -> handleEvent(event) }
        }
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

    private fun initView() = binding.apply {
        searchAddress.textChanges()
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe { addressViewModel.search(it.toString()) }
        addressAdapter = AddressAdapter().apply {
            setItemOnClickListener(object : AddressAdapter.OnItemClickListener {
                override fun click(item: AddressViewModel.Address) {
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