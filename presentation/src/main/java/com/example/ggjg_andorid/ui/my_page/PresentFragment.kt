package com.example.ggjg_andorid.ui.my_page

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentPresentBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel

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