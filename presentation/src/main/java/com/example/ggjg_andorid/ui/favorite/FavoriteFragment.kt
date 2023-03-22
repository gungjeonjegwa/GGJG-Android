package com.example.ggjg_andorid.ui.favorite

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.BreadModel
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.BreadListAdapter
import com.example.ggjg_andorid.adapter.decorator.BreadListDecorator
import com.example.ggjg_andorid.databinding.FragmentFavoriteBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.Extension.customTopScroll
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.DetailViewModel
import com.example.ggjg_andorid.viewmodel.FavoriteViewModel
import com.example.ggjg_andorid.viewmodel.MainViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()
    private lateinit var breadAdapter: BreadListAdapter
    override fun onCreate() {
        repeatOnStart {
            favoriteViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            favoriteViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

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

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        favoriteViewModel.getLikeBread()
        favorite = this@FavoriteFragment
        breadAdapter = BreadListAdapter().apply {
            setItemOnClickListener(object : BreadListAdapter.OnItemClickListener {
                override fun detail(item: BreadModel) {
                    DetailViewModel.id = item.id
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_favoriteFragment_to_detailBreadFragment)
                }

                override fun like(item: BreadModel) {
                    favoriteViewModel.delete(item.id)
                    breadAdapter.submitList(breadAdapter.currentList.filter { it.id != item.id })
                }
            })
        }
        val deviceWidth = requireContext().resources.displayMetrics.widthPixels
        favoriteList.apply {
            itemAnimator = null
            adapter = breadAdapter
            layoutManager = if (deviceWidth <= 1080) GridLayoutManager(
                requireContext(),
                2
            ) else GridLayoutManager(
                requireContext(),
                3
            )
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