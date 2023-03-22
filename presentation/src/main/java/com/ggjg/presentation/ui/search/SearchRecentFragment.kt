package com.ggjg.presentation.ui.search

import androidx.fragment.app.activityViewModels
import com.ggjg.domain.entity.bread.RecentSearchEntity
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.RecentSearchAdapter
import com.ggjg.presentation.databinding.FragmentSearchRecentBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchRecentFragment :
    BaseFragment<FragmentSearchRecentBinding>(R.layout.fragment_search_recent) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: RecentSearchAdapter
    override fun onCreate() {
    }

    override fun createView() {
        searchViewModel.recentSearch()
        mainViewModel.hiddenNav(true)
        initAdapter()
    }

    private fun initAdapter() = binding.recentSearchList.apply {
        this@SearchRecentFragment.adapter = SearchViewModel.adapter
        this@SearchRecentFragment.adapter.setItemOnClickListener(object :
            RecentSearchAdapter.OnItemClickListener {
            override fun delete(item: RecentSearchEntity) {
                searchViewModel.deleteRecentSearch(item.search)
            }

            override fun search(item: RecentSearchEntity) {
                SearchViewModel.search = item.search
                searchViewModel.searchResult()
            }
        })
        adapter = this@SearchRecentFragment.adapter
        layoutManager = FlexboxLayoutManager(requireContext())
    }
}