package com.example.ggjg_andorid.ui.order

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.ReviewImageAdapter
import com.example.ggjg_andorid.databinding.FragmentOrderReviewBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled
import com.example.ggjg_andorid.utils.setOnTextChanged
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.OrderViewModel

class OrderReviewFragment :
    BaseFragment<FragmentOrderReviewBinding>(R.layout.fragment_order_review) {
    private lateinit var reviewImageAdapter: ReviewImageAdapter

    override fun createView() {
        binding.reviewOrder = this
        initView()
    }

    private fun initView() = binding.apply {
        if (OrderViewModel.imgList.size == 2) {
            addImgBtn.setVisible(false)
        } else {
            addImgBtn.setVisible()
        }
        addImgBtn.translationZ = -1f
        reviewImgList.translationZ = -1f
        reviewImageAdapter = ReviewImageAdapter().apply {
            setItemOnClickListener(object : ReviewImageAdapter.OnItemClickListener {
                override fun click(item: String) {
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_orderReviewFragment_to_imageSelectorFragment)
                }
            })
        }
        reviewImgList.apply {
            adapter = reviewImageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        reviewImageAdapter.submitList(OrderViewModel.imgList)
        writeItemQuality.apply {
            setOnTextChanged { p0, _, _, _ ->
                enrollBtn.changeActivatedWithEnabled(!p0.isNullOrBlank() && OrderViewModel.imgList.isNotEmpty())
            }
            setOnFocusChangeListener { _, b ->
                writeItemQuality.isActivated = b
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.addImgBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_orderReviewFragment_to_imageSelectorFragment)
            }
        }
    }
}