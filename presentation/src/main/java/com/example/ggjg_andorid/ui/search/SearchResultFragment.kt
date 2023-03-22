package com.example.ggjg_andorid.ui.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.BreadModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.BreadListAdapter
import com.example.ggjg_andorid.adapter.decorator.BreadListDecorator
import com.example.ggjg_andorid.databinding.FragmentSearchResultBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.DetailViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.SearchViewModel

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var searchResultAdapter: BreadListAdapter
    override fun onCreate() {
        repeatOnStart {
            searchViewModel.searchResultEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            searchViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        mainViewModel.hiddenNav(false)
        initView()
    }

    private fun handleEvent(event: SearchViewModel.SearchResultEvent) = when (event) {
        is SearchViewModel.SearchResultEvent.SearchResult -> {
            if (event.data.isEmpty()) {
                binding.noSearchTxt.visibility = View.VISIBLE
            } else {
                searchResultAdapter.submitList(event.data)
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        searchResultAdapter = BreadListAdapter().apply {
            setItemOnClickListener(object : BreadListAdapter.OnItemClickListener {
                override fun detail(item: BreadModel) {
                    DetailViewModel.id = item.id
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_searchFragment_to_detailBreadFragment)
                }

                override fun like(item: BreadModel) {
                    searchViewModel.like(item.id)
                }
            })
        }
        val deviceWidth = requireContext().resources.displayMetrics.widthPixels
        searchList.apply {
            itemAnimator = null
            adapter = searchResultAdapter
            layoutManager = if (deviceWidth <= 1080) GridLayoutManager(
                requireContext(),
                2
            ) else GridLayoutManager(
                requireContext(),
                3
            )
            addItemDecoration(BreadListDecorator(context))
        }
    }
}