package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.RecentSearchAdapter
import com.example.ggjg_andorid.databinding.FragmentSearchRecentBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchRecentFragment: BaseFragment<FragmentSearchRecentBinding> (R.layout.fragment_search_recent) {
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: RecentSearchAdapter

    override fun createView() {
        searchViewModel.recentSearch()
        initAdapter()
        repeatOnStart {
            searchViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: SearchViewModel.Event) = when (event) {
        is SearchViewModel.Event.RecentSearch -> {
            adapter.submitList(event.recentSearch)
        }

        is SearchViewModel.Event.SuccessDelete -> {
            searchViewModel.recentSearch()
        }
    }

    private fun initAdapter() = binding.recentSearchList.apply {
        this@SearchRecentFragment.adapter = RecentSearchAdapter {
            searchViewModel.deleteRecentSearch(it.search)
        }
        adapter = this@SearchRecentFragment.adapter
        layoutManager = FlexboxLayoutManager(requireContext())
    }
}