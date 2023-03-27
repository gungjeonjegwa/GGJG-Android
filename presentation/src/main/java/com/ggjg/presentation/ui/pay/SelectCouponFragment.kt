package com.ggjg.presentation.ui.pay

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.coupon.CouponEntity
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.CouponAdapter
import com.ggjg.presentation.databinding.FragmentSelectCouponBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.viewmodel.PayViewModel
import event.ErrorEvent

class SelectCouponFragment :
    BaseFragment<FragmentSelectCouponBinding>(R.layout.fragment_select_coupon) {
    private val payViewModel by activityViewModels<PayViewModel>()
    private lateinit var couponAdapter: CouponAdapter
    override fun onCreate() {
        repeatOnStart {
            payViewModel.couponEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            payViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        payViewModel.availableCoupon()
        binding.selectCoupon = this
        initView()
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