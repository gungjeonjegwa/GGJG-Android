package com.example.ggjg_andorid.ui.my_page

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentMyPageEditBinding
import com.example.ggjg_andorid.ui.base.BaseFragment

class MyPageEditFragment : BaseFragment<FragmentMyPageEditBinding>(R.layout.fragment_my_page_edit) {
    override fun createView() {
        binding.myPageEdit = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}