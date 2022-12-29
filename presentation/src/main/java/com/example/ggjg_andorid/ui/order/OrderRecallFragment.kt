package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentOrderRecallBinding
import com.example.ggjg_andorid.ui.base.BaseFragment

class OrderRecallFragment :
    BaseFragment<FragmentOrderRecallBinding>(R.layout.fragment_order_recall) {
    override fun createView() {
        binding.orderRecall = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}