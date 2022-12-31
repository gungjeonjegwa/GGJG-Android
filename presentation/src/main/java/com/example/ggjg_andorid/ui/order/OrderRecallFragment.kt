package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentOrderRecallBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled

class OrderRecallFragment :
    BaseFragment<FragmentOrderRecallBinding>(R.layout.fragment_order_recall) {
    override fun createView() {
        binding.orderRecall = this
    }

    fun onClick(view: View) {
        binding.apply {
            when (view) {
                backBtn -> requireActivity().findNavController(R.id.mainContainer).popBackStack()
                sizeBtn, mixBtn, destroyBtn, expiredBtn, agingBtn, etcBtn -> {
                    listOf(sizeBtn,
                        mixBtn,
                        destroyBtn,
                        expiredBtn,
                        agingBtn,
                        etcBtn,
                        etcBtn).forEach {
                        it.isSelected = it == view
                    }
                    recallBtn.changeActivatedWithEnabled(true)
                }
            }
        }
    }
}