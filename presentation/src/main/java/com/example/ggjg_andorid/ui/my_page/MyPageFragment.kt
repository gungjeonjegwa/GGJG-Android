package com.example.ggjg_andorid.ui.my_page

import android.content.Intent
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentMyPageBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun createView() {
        requireActivity().findNavController(R.id.mainContainer).popBackStack()
        requireActivity().startActivity(Intent(requireActivity(), LoginActivity::class.java))
    }
}