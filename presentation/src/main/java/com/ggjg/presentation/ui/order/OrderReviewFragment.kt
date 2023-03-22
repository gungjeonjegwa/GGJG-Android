package com.ggjg.presentation.ui.order

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.ReviewImageAdapter
import com.ggjg.presentation.databinding.FragmentOrderReviewBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.setOnTextChanged
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.OrderViewModel

class OrderReviewFragment :
    BaseFragment<FragmentOrderReviewBinding>(R.layout.fragment_order_review) {
    private lateinit var reviewImageAdapter: ReviewImageAdapter
    override fun onCreate() {

    }

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