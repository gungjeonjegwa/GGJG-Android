package com.ggjg.presentation.ui.register

import androidx.activity.viewModels
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityRegisterBinding
import com.ggjg.presentation.ui.base.BaseActivity
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.viewmodel.RegisterViewModel
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