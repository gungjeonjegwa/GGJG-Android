package com.example.ggjg_andorid.ui.favorite

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.entity.bread.LikeBreadEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.LikeBreadListAdapter
import com.example.ggjg_andorid.adapter.decorator.BreadListDecorator
import com.example.ggjg_andorid.databinding.FragmentFavoriteBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.Extension.customTopScroll
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.DetailViewModel
import com.example.ggjg_andorid.viewmodel.FavoriteViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()
    private lateinit var breadAdapter: LikeBreadListAdapter
    override fun createView() {
        if (MainViewModel.isLogin) {
            initView()
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
        is FavoriteViewModel.Event.BreadList -> {
            binding.favoriteScrollView.setVisible()
            binding.topScrollBtn.setVisible()
            breadAdapter.submitList(event.data)
        }
        is FavoriteViewModel.Event.Empty -> {
            binding.emptyFavoriteTxt.setVisible()
        }
    }

    private fun initView() = binding.apply {
        favoriteViewModel.getLikeBread()
        favorite = this@FavoriteFragment
        repeatOnStart {
            favoriteViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        breadAdapter = LikeBreadListAdapter().apply {
            setItemOnClickListener(object : LikeBreadListAdapter.OnItemClickListener {
                override fun detail(item: LikeBreadEntity) {
                    DetailViewModel.id = item.id
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_favoriteFragment_to_detailBreadFragment)
                }

                override fun like(item: LikeBreadEntity) {
                    favoriteViewModel.delete(item.id)
                    breadAdapter.submitList(breadAdapter.currentList.filter { it.id != item.id })
                }
            })
        }
        favoriteList.apply {
            itemAnimator = null
            adapter = breadAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(BreadListDecorator(context))
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.topScrollBtn -> {
                binding.favoriteScrollView.customTopScroll(binding.favoriteList)
            }
            R.id.allDeleteBtn -> {
                breadAdapter.currentList.forEach {
                    favoriteViewModel.delete(it.id)
                }
                binding.favoriteScrollView.setVisible(false)
                binding.topScrollBtn.setVisible(false)
                binding.emptyFavoriteTxt.setVisible()
            }
        }
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