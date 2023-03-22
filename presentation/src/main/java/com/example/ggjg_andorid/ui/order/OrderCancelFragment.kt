package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentOrderCancelBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled

class OrderCancelFragment :
    BaseFragment<FragmentOrderCancelBinding>(R.layout.fragment_order_cancel) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.orderCancel = this
        initView()
    }

    private fun initView() = binding.apply {
        val optionList = listOf(justBtn, couponBtn, optionBtn, delayBtn, addressBtn, etcBtn)
        listOf(
            justLayout,
            couponLayout,
            optionLayout,
            delayLayout,
            addressLayout,
            etcLayout
        ).forEachIndexed { i, v ->
            v.setOnClickListener {
                optionList.forEachIndexed { o_i, o_v ->
                    o_v.isSelected = i == o_i
                }
                cancelBtn.changeActivatedWithEnabled(true)
            }
        }
    }

    fun onClick(view: View) {
        binding.apply {
            when (view) {
                backBtn -> {
                    requireActivity().findNavController(R.id.mainContainer).popBackStack()
                }
            }
        }
    }
}