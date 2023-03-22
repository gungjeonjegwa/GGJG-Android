package com.ggjg.presentation.ui.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ggjg.domain.model.BreadModel
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.BreadListAdapter
import com.ggjg.presentation.adapter.decorator.BreadListDecorator
import com.ggjg.presentation.databinding.FragmentSearchResultBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.viewmodel.DetailViewModel
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.SearchViewModel

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