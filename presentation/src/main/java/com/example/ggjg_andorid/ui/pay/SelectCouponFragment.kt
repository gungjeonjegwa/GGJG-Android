package com.example.ggjg_andorid.ui.pay

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.coupon.CouponEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.CouponAdapter
import com.example.ggjg_andorid.databinding.FragmentSelectCouponBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.PayViewModel

class SelectCouponFragment :
    BaseFragment<FragmentSelectCouponBinding>(R.layout.fragment_select_coupon) {
    private val payViewModel by activityViewModels<PayViewModel>()
    private lateinit var couponAdapter: CouponAdapter
    override fun createView() {
        binding.selectCoupon = this
        initView()
        payViewModel.availableCoupon()
        repeatOnStart {
            payViewModel.couponEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            payViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: PayViewModel.CouponEvent) = when (event) {
        is PayViewModel.CouponEvent.Coupon -> {
            couponAdapter.submitList(event.data)
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        couponAdapter = CouponAdapter().apply {
            setItemOnClickListener(object : CouponAdapter.OnItemClickListener {
                override fun click(item: CouponEntity) {
                    if (PayViewModel.selectCouponList.find { it.id == PayViewModel.currentItemPosition } != null) {
                        PayViewModel.selectCouponList =
                            PayViewModel.selectCouponList.plus(
                                PayViewModel.BuyCoupon(
                                    PayViewModel.currentItemPosition,
                                    item.id,
                                    item.price,
                                    0,
                                    item.type
                                )
                            )
                    } else {
                        PayViewModel.selectCouponList =
                            PayViewModel.selectCouponList.filter { it.id != PayViewModel.currentItemPosition }
                        PayViewModel.selectCouponList =
                            PayViewModel.selectCouponList.plus(
                                PayViewModel.BuyCoupon(
                                    PayViewModel.currentItemPosition,
                                    item.id,
                                    item.price,
                                    0,
                                    item.type
                                )
                            )
                    }
                    requireActivity().findNavController(R.id.mainContainer).popBackStack()
                }
            })
        }
        couponList.apply {
            adapter = couponAdapter
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