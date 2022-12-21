package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import com.example.domain.entity.RecentSearchEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.RecentSearchAdapter
import com.example.ggjg_andorid.databinding.FragmentSearchRecentBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchRecentFragment :
    BaseFragment<FragmentSearchRecentBinding>(R.layout.fragment_search_recent) {
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: RecentSearchAdapter

    override fun createView() {
        searchViewModel.recentSearch()
        initAdapter()
    }

    private fun initAdapter() = binding.recentSearchList.apply {
        this@SearchRecentFragment.adapter = SearchViewModel.adapter
        this@SearchRecentFragment.adapter.setItemOnClickListener(object : RecentSearchAdapter.OnItemClickListener {
            override fun delete(item: RecentSearchEntity) {
                searchViewModel.deleteRecentSearch(item.search)
            }

            override fun search(item: RecentSearchEntity) {
                searchViewModel.search(item.search)
            }
        })
        adapter = this@SearchRecentFragment.adapter
        layoutManager = FlexboxLayoutManager(requireContext())
    }
}