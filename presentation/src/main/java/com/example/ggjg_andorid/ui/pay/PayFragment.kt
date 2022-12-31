package com.example.ggjg_andorid.ui.pay

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.order.InitOrderEntity
import com.example.domain.model.AddressModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.PayAdapter
import com.example.ggjg_andorid.databinding.FragmentPayBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.*
import com.example.ggjg_andorid.viewmodel.AddressViewModel
import com.example.ggjg_andorid.viewmodel.PayViewModel
import java.text.DecimalFormat

class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {

    private lateinit var payAdapter: PayAdapter
    private val payViewModel by activityViewModels<PayViewModel>()
    private var totalMoney = 0
    private var totalAmount = 0

    override fun onAttach(context: Context) {
        PayViewModel.address = null
        super.onAttach(context)
    }

    override fun createView() {
        binding.pay = this
        initView()
        AddressViewModel.isPayment = true
        payViewModel.init()
        repeatOnStart {
            payViewModel.eventFlow.collect { event -> eventHandler(event) }
        }
    }

    private fun eventHandler(event: PayViewModel.Event) = when (event) {
        is PayViewModel.Event.InitInfo -> {
            binding.phoneTxt.text = "(${event.data.phone})"
            binding.nameTxt.text = event.data.name
            viewText(event.data.address!!)
        }
        is PayViewModel.Event.NoAddressInitInfo -> {
            binding.setOrderAddressBtn.setVisible()
        }
    }

    private fun viewText(address: AddressModel) = binding.apply {
        setOrderAddressBtn.setVisible(false)
        changeOrderAddressBtn.setVisible()
        orderAddressTxt.text =
            "${address.landNumber} ${address.road} (${address.zipcode})"
        if (!address.detailAddress.isNullOrBlank()) {
            deliveryOrderAddressTxt.text = "상세주소 : ${address.detailAddress}"
        }
        payBtn.changeActivatedWithEnabled(PayViewModel.payMethod != null)
    }

    private fun initView() = binding.apply {
        payAdapter = PayAdapter().apply {
            setItemOnClickListener(object : PayAdapter.OnItemClickListener {
                override fun click(item: String) {

                }
            })
        }
        paymentList.apply {
            adapter = payAdapter
            layoutManager = LinearLayoutManager(context)
        }
        payAdapter.submitList(PayViewModel.shoppingList)
        PayViewModel.shoppingList.forEach {
            if (!it.isSoldOut) {
                totalAmount += it.count
                totalMoney += (it.price + (it.extraMoney ?: 0)) * it.count
            }
        }
        if (totalAmount == 0) {
            deliveryCostTxt.text = "0원"
        } else {
            deliveryCostTxt.text = getString(R.string.delivery_cost_default)
        }
        itemPriceTxt.text = "${totalMoney}원"
        totalTxt.text = "총합 ${totalAmount}개"
        totalCostTxt.text =
            DecimalFormat("#,###").format(totalMoney + (if (totalAmount == 0) 0 else 3000))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.payPhoneBtn, R.id.payCardBtn, R.id.payTransferBtn, R.id.payKakaoBtn -> {
                binding.payBtn.changeActivatedWithEnabled(!binding.orderAddressTxt.text.isNullOrBlank())
                listOf(binding.payPhoneBtn,
                    binding.payCardBtn,
                    binding.payTransferBtn,
                    binding.payKakaoBtn).forEach {
                    it.isActivated = it == view
                    if (it.isActivated) {
                        payViewModel.setPayMethod(it.id)
                        it.setTextColor(requireContext().getColor(R.color.light_gray))
                    } else {
                        it.setTextColor(requireContext().getColor(R.color.dark_gray))
                    }
                }
            }
            R.id.payBtn -> {
                val title = if (PayViewModel.shoppingList.size == 1) {
                    PayViewModel.shoppingList[0].title
                } else {
                    "${PayViewModel.shoppingList[0].title} 외 ${PayViewModel.shoppingList.size - 1}개"
                }
                bootPayCreate(requireActivity().supportFragmentManager,
                    requireContext(),
                    bootPayPayload(title, totalMoney.toDouble())) {
                    payViewModel.buyBread()
                    true
                }
            }
            R.id.setOrderAddressBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_payFragment_to_searchAddressFragment)
            }
            R.id.changeOrderAddressBtn -> {
                if (PayViewModel.defaultAddress == null) {
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_payFragment_to_searchAddressFragment)
                } else {
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_payFragment_to_changeAddressFragment)
                }
            }
        }
    }
}