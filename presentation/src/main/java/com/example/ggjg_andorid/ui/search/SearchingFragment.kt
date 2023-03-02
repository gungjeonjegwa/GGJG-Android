package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.bread.SearchEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.SearchAdapter
import com.example.ggjg_andorid.databinding.FragmentSearchingBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.SearchViewModel

class SearchingFragment : BaseFragment<FragmentSearchingBinding>(R.layout.fragment_searching) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var searchAdapter: SearchAdapter

    override fun createView() {
        mainViewModel.hiddenNav(true)
        initView()
        repeatOnStart {
            searchViewModel.searchingEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            searchViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
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