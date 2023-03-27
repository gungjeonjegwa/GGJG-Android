package com.ggjg.presentation.ui.search

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.bread.SearchEntity
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.SearchAdapter
import com.ggjg.presentation.databinding.FragmentSearchingBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.SearchViewModel

class SearchingFragment : BaseFragment<FragmentSearchingBinding>(R.layout.fragment_searching) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreate() {
        repeatOnStart {
            searchViewModel.searchingEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            searchViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        mainViewModel.hiddenNav(true)
        initView()
    }

    private fun handleEvent(event: SearchViewModel.SearchingEvent) = when (event) {
        is SearchViewModel.SearchingEvent.Search -> {
            searchAdapter.submitList(event.data)
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {}
    }

    private fun initView() = binding.apply {
        searchAdapter = SearchAdapter().apply {
            setItemOnClickListener(object : SearchAdapter.OnItemClickListener {
                override fun click(item: SearchEntity) {
                    SearchViewModel.search = item.title
                    searchViewModel.searchResult()
                }
            })
        }
        searchList.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}