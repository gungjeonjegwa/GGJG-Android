package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import com.example.domain.entity.bread.RecentSearchEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.RecentSearchAdapter
import com.example.ggjg_andorid.databinding.FragmentSearchRecentBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchRecentFragment :
    BaseFragment<FragmentSearchRecentBinding>(R.layout.fragment_search_recent) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: RecentSearchAdapter

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