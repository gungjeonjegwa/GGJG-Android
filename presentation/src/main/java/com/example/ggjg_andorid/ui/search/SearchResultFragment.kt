package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentSearchResultBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun createView() {
        mainViewModel.hiddenNav(false)
    }
}