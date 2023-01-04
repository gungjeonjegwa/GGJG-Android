package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.SearchResultAdapter
import com.example.ggjg_andorid.adapter.decorator.BreadListDecorator
import com.example.ggjg_andorid.databinding.FragmentSearchResultBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.DetailViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.SearchViewModel

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var searchResultAdapter: SearchResultAdapter
    override fun createView() {
        mainViewModel.hiddenNav(false)
        initView()
        repeatOnStart {
            searchViewModel.searchResultEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: SearchViewModel.SearchResultEvent) = when (event) {
        is SearchViewModel.SearchResultEvent.SearchResult -> {
            searchResultAdapter.submitList(event.data)
        }
    }

    private fun initView() = binding.apply {
        searchResultAdapter = SearchResultAdapter().apply {
            setItemOnClickListener(object : SearchResultAdapter.OnItemClickListener {
                override fun detail(item: SearchResultEntity) {
                    DetailViewModel.id = item.id
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_searchFragment_to_detailBreadFragment)
                }

                override fun like(item: SearchResultEntity) {
                    searchViewModel.like(item.id)
                }
            })
        }
        val deviceWidth = requireContext().resources.displayMetrics.widthPixels
        searchList.apply {
            itemAnimator = null
            adapter = searchResultAdapter
            layoutManager = if (deviceWidth <= 1080) GridLayoutManager(requireContext(),
                2) else GridLayoutManager(
                requireContext(),
                3)
            addItemDecoration(BreadListDecorator(context))
        }
    }
}