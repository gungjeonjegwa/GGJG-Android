package com.ggjg.presentation.ui.my_page

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentPresentBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.viewmodel.MainViewModel

class PresentFragment : BaseFragment<FragmentPresentBinding>(R.layout.fragment_present) {
    private val mainViewModel by activityViewModels<MainViewModel>()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel.hiddenNav(true)
    }

    override fun onDetach() {
        super.onDetach()
        mainViewModel.hiddenNav(false)
    }

    override fun onCreate() {
        
    }

    override fun createView() {
        binding.present = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
    }
}