package com.example.ggjg_andorid.ui.search

import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentSearchBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.*
import com.example.ggjg_andorid.viewmodel.SearchViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchViewModel by activityViewModels<SearchViewModel>()

    override fun createView() {
        binding.search = this
        initView()
        repeatOnStart {
            searchViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: SearchViewModel.Event) = when (event) {
        is SearchViewModel.Event.RecentSearch -> {
            SearchViewModel.adapter.submitList(event.recentSearch)
        }
        is SearchViewModel.Event.SuccessDelete -> {
            searchViewModel.recentSearch()
        }
        is SearchViewModel.Event.Search -> {
            binding.searchBread.setText(SearchViewModel.search)
            keyboardHide(requireActivity(), listOf(binding.searchBread))
            viewFragment(SearchResultFragment())
        }
    }

    private fun initView() = binding.apply {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        viewFragment(SearchRecentFragment())
        keyboardShow(requireActivity(), searchBread)
        searchBread.run {
            Handler().postDelayed({
                text = null
                setOnTextChanged { p0, _, _, _ ->
                    SearchViewModel.search = p0.toString()
                    if (!p0.isNullOrBlank()) {
                        viewFragment(SearchingFragment())
                    } else {
                        viewFragment(SearchRecentFragment())
                    }
                }
            }, 1)
            setOnFocusChangeListener { _, b ->
                searchDivide.setVisible(b)
            }
            setOnEditorActionListener { _, i, _ ->
                when (i) {
                    IME_ACTION_SEARCH -> {
                        keyboardHide(requireActivity(), listOf(binding.searchBread))
                        searchViewModel.search()
                        viewFragment(SearchResultFragment())
                    }
                }
                true
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                keyboardHide(requireActivity(), listOf(binding.searchBread))
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.searchLayout -> {
                keyboardHide(requireActivity(), listOf(binding.searchBread))
            }
            R.id.searchBtn -> {
                keyboardHide(requireActivity(), listOf(binding.searchBread))
                searchViewModel.search()
                viewFragment(SearchResultFragment())
            }
        }
    }

    private fun viewFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.searchContainer, fragment
        ).commit()
    }
}