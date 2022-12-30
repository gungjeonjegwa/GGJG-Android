package com.example.ggjg_andorid.ui.image

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Index.Order
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.ImageAdapter
import com.example.ggjg_andorid.databinding.FragmentImageSelectorBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.FilePaths
import com.example.ggjg_andorid.utils.getDirectoryPaths
import com.example.ggjg_andorid.utils.getFilePaths
import com.example.ggjg_andorid.utils.getImageDirectory
import com.example.ggjg_andorid.viewmodel.OrderViewModel

class ImageSelectorFragment :
    BaseFragment<FragmentImageSelectorBinding>(R.layout.fragment_image_selector) {
    private lateinit var imgAdapter: ImageAdapter
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