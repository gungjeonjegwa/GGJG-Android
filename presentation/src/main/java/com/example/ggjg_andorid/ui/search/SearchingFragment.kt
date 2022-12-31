package com.example.ggjg_andorid.ui.search

import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentSearchingBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel

class SearchingFragment : BaseFragment<FragmentSearchingBinding>(R.layout.fragment_searching) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun createView() {
        mainViewModel.hiddenNav(true)
    }
}