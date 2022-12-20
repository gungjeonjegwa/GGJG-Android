package com.example.ggjg_andorid.ui.search

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.RecentSearchAdapter
import com.example.ggjg_andorid.databinding.FragmentSearchBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.keyboardHide
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setOnTextChanged
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.SearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val searchViewModel by activityViewModels<SearchViewModel>()
    override fun createView() {
        binding.search = this
        mainViewModel.hiddenNav(true)
        initView()
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
                viewFragment(SearchResultFragment())
            }
        }
    }

    private fun initView() = binding.apply {
        viewFragment(SearchRecentFragment())
        searchBread.setOnTextChanged { p0, _, _, _ ->
            if (p0.isNullOrBlank()) {
                viewFragment(SearchRecentFragment())
            } else {
                viewFragment(SearchingFragment())
            }
        }
    }

    private fun viewFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.searchContainer, fragment
        ).commit()
    }
}