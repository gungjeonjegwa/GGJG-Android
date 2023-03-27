package com.ggjg.presentation.ui.search

import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentSearchBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setOnTextChanged
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.utils.keyboardHide
import com.ggjg.presentation.utils.keyboardShow
import com.ggjg.presentation.viewmodel.SearchViewModel
import com.jakewharton.rxbinding3.widget.textChanges
import event.ErrorEvent
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val searchViewModel by activityViewModels<SearchViewModel>()
    override fun onCreate() {
        repeatOnStart {
            searchViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            searchViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.search = this
        initView()
    }

    private fun handleEvent(event: SearchViewModel.Event) = when (event) {
        is SearchViewModel.Event.RecentSearch -> {
            SearchViewModel.adapter.submitList(event.recentSearch)
        }
        is SearchViewModel.Event.SuccessDelete -> {
            searchViewModel.recentSearch()
        }
        is SearchViewModel.Event.SearchResult -> {
            binding.searchBread.setText(SearchViewModel.search)
            keyboardHide(requireActivity(), listOf(binding.searchBread))
            viewFragment(SearchResultFragment())
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {}
    }

    private fun initView() = binding.apply {
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        viewFragment(SearchRecentFragment())
        keyboardShow(requireActivity(), searchBread)
        searchBread.run {
            textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe { searchViewModel.search() }
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
                        keyboardHide(requireActivity(), listOf(searchBread))
                        searchViewModel.searchResult()
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
                searchViewModel.searchResult()
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