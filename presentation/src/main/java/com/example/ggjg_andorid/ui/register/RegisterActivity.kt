package com.example.ggjg_andorid.ui.register

import androidx.activity.viewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityRegisterBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val registerViewModel by viewModels<RegisterViewModel>()
    override fun createView() {
        repeatOnStart {
            registerViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            registerViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.registerContainer, RegisterFirstFragment()).commit()
    }

    private fun handleEvent(event: RegisterViewModel.Event) = when (event) {
        is RegisterViewModel.Event.Success -> {
            finish()
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {}
    }
}