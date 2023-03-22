package com.ggjg.presentation.ui.coupon

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.coupon.CouponEntity
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.CouponAdapter
import com.ggjg.presentation.databinding.FragmentCouponBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.viewmodel.CouponViewModel
import com.ggjg.presentation.viewmodel.MainViewModel

class CouponFragment : BaseFragment<FragmentCouponBinding>(R.layout.fragment_coupon) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val couponViewModel by activityViewModels<CouponViewModel>()
    private lateinit var couponAdapter: CouponAdapter

    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        super.onDetach()
    }

    override fun onResume() {
        couponViewModel.allCoupon()
        super.onResume()
    }

    override fun onCreate() {
        repeatOnStart { couponViewModel.eventFlow.collect { event -> handleEvent(event) } }
        repeatOnStart { couponViewModel.errorEventFlow.collect { event -> handleEvent(event) } }
    }

    override fun createView() {
        couponViewModel.allCoupon()
        mainViewModel.hiddenNav(true)
        binding.coupon = this
        initView()
    }

    private fun handleEvent(event: CouponViewModel.Event) = when (event) {
        is CouponViewModel.Event.CouponList -> {
            binding.haveCouponTxt.text = "${event.data.size}장"
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
            R.id.addCouponBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_couponFragment_to_addCouponFragment)
            }
        }
    }
}