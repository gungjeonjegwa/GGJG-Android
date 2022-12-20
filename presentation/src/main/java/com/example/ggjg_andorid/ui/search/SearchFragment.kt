package com.example.ggjg_andorid.ui.search

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentSearchBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.keyboardHide
import com.example.ggjg_andorid.viewmodel.MainViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun createView() {
        binding.search = this
        mainViewModel.hiddenNav(true)
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
            }
        }
    }
}