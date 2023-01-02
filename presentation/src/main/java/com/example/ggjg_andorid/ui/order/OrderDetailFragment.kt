package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.OrderDetailAdapter
import com.example.ggjg_andorid.databinding.FragmentOrderDetailBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.OrderViewModel
import java.text.DecimalFormat

class OrderDetailFragment :
    BaseFragment<FragmentOrderDetailBinding>(R.layout.fragment_order_detail) {
    private val orderViewModel by activityViewModels<OrderViewModel>()
    private lateinit var adapter: OrderDetailAdapter
    override fun createView() {
        binding.orderDetail = this
        orderViewModel.detailOrder()
        initView()
        repeatOnStart {
            orderViewModel.detailEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: OrderViewModel.DetailEvent) = when (event) {
        is OrderViewModel.DetailEvent.DetailOrder -> {
            binding.apply {
                phoneTxt.text = event.data.phone
                nameTxt.text = event.data.name
                orderCodeTxt.text = "주문번호 : ${event.data.orderId}"
                var totalAmount = 0
                var totalMoney = 0
                event.data.items.forEach {
                    totalAmount += it.count
                    totalMoney += if (it.discountPrice != null) it.discountPrice!! else it.price
                }
                itemPriceTxt.text = "${totalMoney}원"
                totalTxt.text = "총합 ${totalAmount}개"
                totalCostTxt.text =
                    DecimalFormat("#,###").format(totalMoney + 3000)
                orderAddressTxt.text =
                    "${event.data.address.landNumber} ${event.data.address.road} (${event.data.address.zipcode})"
                if (!event.data.address.detailAddress.isNullOrBlank()) {
                    deliveryOrderAddressTxt.text = "상세주소 : ${event.data.address.detailAddress}"
                }
            }
            adapter.submitList(event.data.items)
        }
    }

    private fun initView() = binding.apply {
        adapter = OrderDetailAdapter()
        paymentList.apply {
            adapter = this@OrderDetailFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}