package com.ggjg.presentation.ui.splash

import android.content.Intent
import android.os.Handler
import androidx.activity.viewModels
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivitySplashBinding
import com.ggjg.presentation.ui.base.BaseActivity
import com.ggjg.presentation.ui.main.MainActivity
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.viewmodel.SplashViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun createView() {
        repeatOnStart {
            splashViewModel.eventFlow.collect { handleEvent(it) }
        }
        saveToken()
    }

    private fun handleEvent(event: SplashViewModel.Event) = when (event) {
        is SplashViewModel.Event.Next -> {
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 500)
        }
    }

    private fun saveToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { token ->
                if (token.isSuccessful) {
                    splashViewModel.saveToken(deviceToken = token.result)
                }
            }
    }
}