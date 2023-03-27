package com.ggjg.presentation.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityMainBinding
import com.ggjg.presentation.ui.base.BaseActivity
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun createView() {
        initBottomNav()
        repeatOnStart {
            mainViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            mainViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: MainViewModel.Event) = when (event) {
        is MainViewModel.Event.IsHiddenNav -> {
            if (event.status) {
                binding.bottomNavTopLine.setGuidelinePercent(1f)
            } else {
                binding.bottomNavTopLine.setGuidelinePercent(0.93f)
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initBottomNav() {
        val navController =
            supportFragmentManager.findFragmentById(R.id.mainContainer)?.findNavController()
        val nav = binding.bottomNav as BottomNavigationView
        navController?.let {
            nav.setupWithNavController(navController)
        }
    }
}