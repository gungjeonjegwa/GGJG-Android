package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentOrderCancelBinding
import com.example.ggjg_andorid.ui.base.BaseFragment

class OrderCancelFragment :
    BaseFragment<FragmentOrderCancelBinding>(R.layout.fragment_order_cancel) {
    override fun createView() {
        binding.orderCancel = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}