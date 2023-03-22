package com.ggjg.presentation.ui.image

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.ImageAdapter
import com.ggjg.presentation.adapter.decorator.ImageListDecorator
import com.ggjg.presentation.databinding.FragmentImageSelectorBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.getImageDirectory
import com.ggjg.presentation.viewmodel.OrderViewModel

class ImageSelectorFragment :
    BaseFragment<FragmentImageSelectorBinding>(R.layout.fragment_image_selector) {
    private lateinit var imgAdapter: ImageAdapter
    override fun onCreate() {

    }

    override fun createView() {
        binding.imageSelector = this
        imgAdapter = ImageAdapter().apply {
            setItemOnClickListener(object : ImageAdapter.OnItemClickListener {
                override fun click(item: String) {
                    if (OrderViewModel.imgList.contains(item)) {
                        OrderViewModel.imgList = OrderViewModel.imgList.filter { it != item }
                    } else {
                        OrderViewModel.imgList = OrderViewModel.imgList.plus(item)
                    }
                }
            })
        }
        binding.imageList.apply {
            adapter = imgAdapter
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(ImageListDecorator(context))
        }
        imgAdapter.submitList(getImageDirectory())
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.closeBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}