package com.example.ggjg_andorid.ui.home

import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentHomeBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private lateinit var categoryList: List<View>

    override fun createView() {
        initView()
        homeViewModel.setTag(binding.allBtn)
        repeatOnStart {
            homeViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: HomeViewModel.Event) = when (event) {
        is HomeViewModel.Event.Category -> {
            categoryList.forEach {
                it.isSelected = it == event.tag
            }
        }
    }

    private fun initView() = binding.apply {
        categoryList = listOf(allBtn, breadBtn, cakeBtn, cookieBtn, presentBtn)
        scrollView.run {
            header = binding.menuBar
        }
        categoryList.forEach {
            it.setOnClickListener { tag ->
                homeViewModel.setTag(tag)
            }
        }
    }
}