package com.example.ggjg_andorid.ui.coupon

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentCouponBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel

class CouponFragment : BaseFragment<FragmentCouponBinding>(R.layout.fragment_coupon) {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        super.onDetach()
    }

    override fun createView() {
        binding.coupon = this
        mainViewModel.hiddenNav(true)
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