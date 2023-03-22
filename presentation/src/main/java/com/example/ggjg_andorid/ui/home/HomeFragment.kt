package com.example.ggjg_andorid.ui.home

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.domain.entity.bread.BreadEntity
import com.example.domain.model.BreadModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.BannerAdapter
import com.example.ggjg_andorid.adapter.BreadListAdapter
import com.example.ggjg_andorid.adapter.decorator.BreadListDecorator
import com.example.ggjg_andorid.adapter.listener.EndlessRecyclerViewScrollListener
import com.example.ggjg_andorid.databinding.FragmentHomeBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.Extension.customTopScroll
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.DetailViewModel
import com.example.ggjg_andorid.viewmodel.HomeViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    SwipeRefreshLayout.OnRefreshListener {
    private val homeViewModel by activityViewModels<HomeViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var categoryList: List<View>
    private lateinit var listener: EndlessRecyclerViewScrollListener
    private lateinit var layoutManager: GridLayoutManager
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
        binding.scrollView.scrollTo(0, 0)
    }

    override fun onCreate() {
        repeatOnStart {
            homeViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            homeViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
        observeCategory()
    }

    override fun createView() {
        HomeViewModel.apply {
            page = 0
            isLast = false
        }
        mainViewModel.hiddenNav(false)
        homeViewModel.getBanner()
        homeViewModel.getBread()
        binding.home = this
        initView()
    }

    private fun handleEvent(event: HomeViewModel.Event) = when (event) {
        is HomeViewModel.Event.Banner -> {
            maxSize = event.bannerList.size
            binding.bannerContainer.adapter = BannerAdapter(event.bannerList, requireContext())
            changePage()
        }
        is HomeViewModel.Event.Bread -> {
            binding.swipeLayout.isRefreshing = false
            adapter.submitList(event.breadList)
        }
        is HomeViewModel.Event.AddBread -> {
            adapter.submitList(adapter.currentList.plus(event.breadList))
            binding.moreProgress.setVisible(false)
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun observeCategory() = homeViewModel.category.observe(this) {
        homeViewModel.getBread()
    }

    private fun initView() = binding.apply {
        categoryList = listOf(allBtn, breadBtn, cakeBtn, cookieBtn, presentBtn)
        if (homeViewModel.category.value == null) {
            allBtn.isSelected = true
        } else {
            categoryList.forEach { view ->
                view.isSelected = view.id == homeViewModel.category.value
            }
        }
        val deviceWidth = requireContext().resources.displayMetrics.widthPixels
        val bannerLayoutParams = bannerContainer.layoutParams
        bannerLayoutParams.height =
            (requireContext().resources.displayMetrics.heightPixels * 0.3).toInt()
        swipeLayout.setOnRefreshListener(this@HomeFragment)
        bannerContainer.layoutParams = bannerLayoutParams
        layoutManager =
            if (deviceWidth <= 1080) GridLayoutManager(requireContext(), 2) else GridLayoutManager(
                requireContext(),
                3
            )
        listener =
            object : EndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    if (!HomeViewModel.isLast) {
                        moreProgress.setVisible()
                        homeViewModel.getBread()
                    }
                }
            }
        adapter = BreadListAdapter()
        adapter.setItemOnClickListener(object : BreadListAdapter.OnItemClickListener {
            override fun detail(item: BreadModel) {
                DetailViewModel.id = item.id
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_homeFragment_to_detailBreadFragment)
            }

            override fun like(item: BreadModel) {
                homeViewModel.like(item.id)
            }
        })
        scrollView.run {
            header = menuBar
        }
        categoryList.forEach {
            it.setOnClickListener { tag ->
                listener.resetState()
                HomeViewModel.apply {
                    page = 0
                    isLast = false
                }
                homeViewModel.setCategory(tag.id)
                categoryList.forEach { view ->
                    view.isSelected = view.id == homeViewModel.category.value
                }
            }
        }
        breadList.run {
            itemAnimator = null
            adapter = this@HomeFragment.adapter
            layoutManager = this@HomeFragment.layoutManager
            addItemDecoration(BreadListDecorator(context))
            addOnScrollListener(listener)
        }
        bannerContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
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

    fun onClick(view: View) {
        when (view.id) {
            R.id.topScrollBtn -> {
                binding.scrollView.customTopScroll(binding.breadList)
            }
        }
    }

    override fun onRefresh() {
        HomeViewModel.apply {
            page = 0
            isLast = false
        }
        listener.resetState()
        homeViewModel.getBread()
    }
}