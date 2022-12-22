package com.example.ggjg_andorid.ui.home

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.BannerAdapter
import com.example.ggjg_andorid.adapter.BreadListAdapter
import com.example.ggjg_andorid.adapter.BreadListDecorator
import com.example.ggjg_andorid.databinding.FragmentHomeBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.HomeViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel
import kotlin.math.max

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var categoryList: List<View>
    private lateinit var adapter: BreadListAdapter
    private val autoTime: Long = 3000
    private var maxSize = 0
    private val handler = HomeBannerHandler()

    override fun onResume() {
        super.onResume()
        autoScroll(autoTime)
    }

    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }

    override fun createView() {
        initView()
        mainViewModel.hiddenNav(false)
        homeViewModel.setTag(binding.allBtn)
        homeViewModel.getBanner()
        homeViewModel.allBread()
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
        is HomeViewModel.Event.Banner -> {
            maxSize = event.bannerList.size
            binding.bannerContainer.adapter = BannerAdapter(event.bannerList)
            changePage()
        }
        is HomeViewModel.Event.Bread -> {
            adapter.submitList(event.breadList)
        }
    }

    private fun initView() = binding.apply {
        categoryList = listOf(allBtn, breadBtn, cakeBtn, cookieBtn, presentBtn)
        adapter = BreadListAdapter {

        }
        scrollView.run {
            header = binding.menuBar
            stickListener = {
                scrollView.isNestedScrollingEnabled = false
                breadList.isNestedScrollingEnabled = true
            }
            freeListener = {
                scrollView.isNestedScrollingEnabled = true
                breadList.isNestedScrollingEnabled = false
            }
        }
        categoryList.forEach {
            it.setOnClickListener { tag ->
                homeViewModel.setTag(tag)
            }
        }
        breadList.run {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(BreadListDecorator(context))
        }
        bannerContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit

            override fun onPageSelected(position: Int) {
                changePage()
            }

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    ViewPager.SCROLL_STATE_DRAGGING -> autoScrollStop()
                    ViewPager.SCROLL_STATE_IDLE -> autoScroll(autoTime)
                }
            }
        })
    }

    private fun changePage() = binding.run {
        bannerIndicator.text = "${bannerContainer.currentItem + 1} / $maxSize"
    }

    private fun autoScroll(time: Long) {
        handler.removeMessages(0)
        handler.sendEmptyMessageDelayed(0, time)
    }

    private fun autoScrollStop() {
        handler.removeMessages(0)
    }

    private inner class HomeBannerHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                binding.bannerContainer.run {
                    setCurrentItem(
                        if (this.currentItem >= maxSize - 1) 0 else ++this.currentItem,
                        true
                    )
                }
                autoScroll(autoTime)
            }
        }
    }
}