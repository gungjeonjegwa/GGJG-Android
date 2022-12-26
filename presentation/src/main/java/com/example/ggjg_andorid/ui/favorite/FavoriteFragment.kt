package com.example.ggjg_andorid.ui.favorite

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentFavoriteBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.FavoriteViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.ShoppingListViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()
    override fun createView() {
        if (MainViewModel.isLogin) {
            initView()
            repeatOnStart {
                favoriteViewModel.eventFlow.collect { event -> handleEvent(event) }
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

    private fun handleEvent(event: FavoriteViewModel.Event) = when (event) {

        else -> {}
    }

    private fun initView() = binding.apply {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        } else {
            initView()
        }
    }
}