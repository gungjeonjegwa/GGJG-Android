package com.example.ggjg_andorid.ui.pay

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.PayAdapter
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.databinding.FragmentPayBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.PayViewModel
import com.example.ggjg_andorid.viewmodel.ShoppingListViewModel
import java.text.DecimalFormat

class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {

    private lateinit var payAdapter: PayAdapter
    private val payViewModel by activityViewModels<PayViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BaseActivity<ActivityMainBinding>).setOnKeyBackPressedListener(object :
            BaseActivity.OnKeyBackPressedListener {
            override fun onBack() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@PayFragment).commit()
            }
        })
    }

    override fun onDetach() {
        (activity as BaseActivity<ActivityMainBinding>).deleteOnKeyBackPressedListener()
        super.onDetach()
    }

    override fun createView() {
        binding.pay = this
        initView()
        payViewModel.init()
        repeatOnStart {
            payViewModel.eventFlow.collect { event -> eventHandler(event) }
        }
    }

    private fun eventHandler(event: PayViewModel.Event) = when (event) {
        is PayViewModel.Event.InitInfo -> {
            binding.phoneTxt.text = "(${event.data.phone})"
            binding.nameTxt.text = event.data.name
            event.data.address.let {
                if (it != null) {
                    binding.setOrderAddressBtn.setVisible(false)
                    binding.changeOrderAddressBtn.setVisible()
                    binding.orderAddressTxt.text =
                        "${it.landNumber} ${it.road} (${it.detailAddress}) (${it.zipcode})"
                }
            }
        }
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
        var totalMoney = 0
        var totalAmount = 0
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
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
            R.id.payPhoneBtn, R.id.payCardBtn, R.id.payTransferBtn, R.id.payKakaoBtn -> {
                listOf(binding.payPhoneBtn, binding.payCardBtn, binding.payTransferBtn, binding.payKakaoBtn).forEach {
                    it.isActivated = it == view
                    payViewModel.setPayMethod(it.id)
                    if (it.isActivated) {
                        it.setTextColor(requireContext().getColor(R.color.light_gray))
                    } else {
                        it.setTextColor(requireContext().getColor(R.color.dark_gray))
                    }
                }
            }
        }
    }
}