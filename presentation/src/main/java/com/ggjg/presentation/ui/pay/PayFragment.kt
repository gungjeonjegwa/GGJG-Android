package com.ggjg.presentation.ui.pay

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.domain.model.AddressModel
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.PayAdapter
import com.ggjg.presentation.databinding.FragmentPayBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.ui.toast.GGJGToast
import com.ggjg.presentation.utils.bootPayCreate
import com.ggjg.presentation.utils.bootPayPayload
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.utils.extension.toTotalMoney
import com.ggjg.presentation.viewmodel.AddressViewModel
import com.ggjg.presentation.viewmodel.PayViewModel
import java.text.DecimalFormat
import kotlin.math.roundToInt

class PayFragment : BaseFragment<FragmentPayBinding>(R.layout.fragment_pay) {

    private lateinit var payAdapter: PayAdapter
    private val payViewModel by activityViewModels<PayViewModel>()
    private var totalMoney = 0
    private var totalAmount = 0

    override fun onAttach(context: Context) {
        PayViewModel.address = null
        PayViewModel.selectCouponList = listOf()
        PayViewModel.payMethod = null
        super.onAttach(context)
    }

    override fun onDetach() {
        if (PayViewModel.orderNumber != null) {
            payViewModel.cancelBuy()
        }
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        viewTotal()
    }

    override fun onCreate() {
        repeatOnStart {
            payViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            payViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        payViewModel.init()
        binding.pay = this
        initView()
        AddressViewModel.isPayment = true
    }

    private fun handleEvent(event: PayViewModel.Event) = when (event) {
        is PayViewModel.Event.InitInfo -> {
            binding.phoneTxt.text = "(${event.data.phone})"
            binding.nameTxt.text = event.data.name
            viewText(event.data.address!!)
        }
        is PayViewModel.Event.NoAddressInitInfo -> {
            binding.setOrderAddressBtn.setVisible()
        }
        is PayViewModel.Event.SuccessPay -> {
            GGJGToast.createText(requireContext(), "결제가 완료되었습니다.", true)
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

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
                override fun click(item: MyBasketEntity, position: Int) {
                    PayViewModel.currentItemId = item.breadId
                    PayViewModel.currentItemPosition = position
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_payFragment_to_selectCouponFragment)
                }

                override fun cancel() {
                    viewTotal()
                }
            })
        }
        when (PayViewModel.payMethod) {
            "휴대폰" -> payPhoneBtn.isActivated = true
            "카드" -> payCardBtn.isActivated = true
            "계좌이체" -> payTransferBtn.isActivated = true
            "카카오페이" -> payKakaoBtn.isActivated = true
        }
        paymentList.apply {
            adapter = payAdapter
            layoutManager = LinearLayoutManager(context)
        }
        payAdapter.submitList(PayViewModel.shoppingList)
    }

    private fun viewTotal() = binding.apply {
        totalMoney = 0
        totalAmount = 0
        PayViewModel.shoppingList.forEachIndexed { i, it ->
            val coupon = PayViewModel.selectCouponList.find { it.id == i }
            var discount = 0
            if (coupon != null) {
                discount =
                    if (coupon.type == "NORMAL") coupon.price else (it.price.toTotalMoney(
                        it.extraMoney,
                        it.count
                    ) * (coupon.price.toFloat() / 100)).roundToInt()
                PayViewModel.selectCouponList.map { selectCoupon ->
                    if (selectCoupon.id == coupon.id) {
                        selectCoupon.discountPrice =
                            it.price.toTotalMoney(it.extraMoney, it.count) - discount
                    }
                }
            }

            if (!it.isSoldOut) {
                totalAmount += it.count
                totalMoney += it.price.toTotalMoney(
                    it.extraMoney,
                    it.count
                ) - discount
            }
        }
        deliveryCostTxt.text = getString(R.string.delivery_cost_default)
        itemPriceTxt.text = "${totalMoney}원"
        totalTxt.text = "총합 ${totalAmount}개"
        totalCostTxt.text =
            DecimalFormat("#,###").format(totalMoney + 3000)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.payPhoneBtn, R.id.payCardBtn, R.id.payTransferBtn, R.id.payKakaoBtn -> {
                binding.payBtn.changeActivatedWithEnabled(!binding.orderAddressTxt.text.isNullOrBlank())
                listOf(
                    binding.payPhoneBtn,
                    binding.payCardBtn,
                    binding.payTransferBtn,
                    binding.payKakaoBtn
                ).forEach {
                    it.isActivated = it == view
                    if (it.isActivated) {
                        payViewModel.setPayMethod(it.id)
                    }
                }
            }
            R.id.payBtn -> {
                GGJGToast.createText(requireContext(), "현재 지원되지 않는 기능입니다.", false)
//                val title = if (PayViewModel.shoppingList.size == 1) {
//                    PayViewModel.shoppingList[0].title
//                } else {
//                    "${PayViewModel.shoppingList[0].title} 외 ${PayViewModel.shoppingList.size - 1}개"
//                }
//                bootPayCreate(
//                    requireActivity().supportFragmentManager,
//                    requireContext(),
//                    bootPayPayload(title, totalMoney.toDouble())
//                ) {
//                    payViewModel.buyBread()
//                }
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