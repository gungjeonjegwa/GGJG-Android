package com.example.ggjg_andorid.ui.main

import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity: BaseActivity<ActivityMainBinding> (R.layout.activity_main) {
    override fun createView() {
        initBottomNav()
    }

    private fun initBottomNav() {
        val navController = supportFragmentManager.findFragmentById(R.id.mainContainer)?.findNavController()
        val nav = binding.bottomNav as BottomNavigationView
        navController?.let {
            nav.setupWithNavController(navController)
        }
    }
}