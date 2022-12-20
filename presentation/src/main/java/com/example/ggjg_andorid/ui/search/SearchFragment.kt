package com.example.ggjg_andorid.ui.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.RecentSearchAdapter
import com.example.ggjg_andorid.databinding.FragmentSearchBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.keyboardHide
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: RecentSearchAdapter
    override fun createView() {
        binding.search = this
        mainViewModel.hiddenNav(true)
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
        this@SearchFragment.adapter = RecentSearchAdapter {
            searchViewModel.deleteRecentSearch(it.search)
        }
        adapter = this@SearchFragment.adapter
        layoutManager = FlexboxLayoutManager(requireContext())
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                mainViewModel.hiddenNav(false)
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.searchLayout -> {
                keyboardHide(requireActivity(), binding.searchBread)
            }
            R.id.searchBtn -> {
                keyboardHide(requireActivity(), binding.searchBread)
                searchViewModel.search(binding.searchBread.text.toString())
            }
        }
    }
}