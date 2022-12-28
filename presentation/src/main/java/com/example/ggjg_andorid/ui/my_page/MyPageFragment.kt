package com.example.ggjg_andorid.ui.my_page

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.StampAdapter
import com.example.ggjg_andorid.databinding.FragmentMyPageBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.ProfileViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private lateinit var stampAdapter: StampAdapter
    override fun createView() {
        if (MainViewModel.isLogin) {
            binding.myPage = this
            initView()
            repeatOnStart {
                profileViewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        } else {
            this.startActivityForResult(
                Intent(
                    requireActivity(),
                    LoginActivity::class.java
                ), 0
            )
        }
    }

    private fun handleEvent(event: ProfileViewModel.Event) = when (event) {
        ProfileViewModel.Event.Success -> {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
    }

    private fun initView() = binding.apply {
        stampAdapter = StampAdapter()
        stampList.apply {
            adapter = stampAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
        stampAdapter.submitList((1..10).map { it.toString() })
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.logoutBtn -> {
                profileViewModel.logout()
            }
            R.id.settingBtn -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, MyPagePrivacyFragment()).commit()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        } else {
            binding.myPage = this
            initView()
            repeatOnStart {
                profileViewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        }
    }
}