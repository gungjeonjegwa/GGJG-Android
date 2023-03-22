package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentOrderRecallBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled

class OrderRecallFragment :
    BaseFragment<FragmentOrderRecallBinding>(R.layout.fragment_order_recall) {
    override fun onCreate() {
        
    }

    override fun createView() {
        binding.orderRecall = this
        initView()
    }

    private fun initView() = binding.apply {
        val optionList = listOf(sizeBtn, mixBtn, destroyBtn, expiredBtn, agingBtn, etcBtn)
        listOf(
            sizeLayout,
            mixLayout,
            destroyLayout,
            expiredLayout,
            agingLayout,
            etcLayout,
            etcLayout
        ).forEachIndexed { i, v ->
            v.setOnClickListener {
                optionList.forEachIndexed { o_i, o_v ->
                    o_v.isSelected = i == o_i
                }
                recallBtn.changeActivatedWithEnabled(true)
            }
        }
    }

    fun onClick(view: View) {
        binding.apply {
            when (view) {
                backBtn -> requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}