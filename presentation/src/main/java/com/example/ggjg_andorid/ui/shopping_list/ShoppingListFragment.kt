package com.example.ggjg_andorid.ui.shopping_list

import android.content.Intent
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentShoppingListBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.viewmodel.ProfileViewModel
import com.example.ggjg_andorid.viewmodel.ShoppingListViewModel

class ShoppingListFragment :
    BaseFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list) {
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()
    override fun createView() {
        shoppingListViewModel.isLogin()
        repeatOnStart {
            shoppingListViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: ShoppingListViewModel.Event) = when (event) {
        is ShoppingListViewModel.Event.IsLogin -> {
            if (!event.isLogin) {
                this.startActivityForResult(
                    Intent(
                        requireActivity(),
                        LoginActivity::class.java
                    ), 0
                )
            } else {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        } else {

        }
    }
}