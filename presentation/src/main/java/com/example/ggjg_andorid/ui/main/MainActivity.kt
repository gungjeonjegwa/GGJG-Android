package com.example.ggjg_andorid.ui.main

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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