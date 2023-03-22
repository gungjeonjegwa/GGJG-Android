package com.ggjg.presentation.ui.splash

import android.content.Intent
import android.os.Handler
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivitySplashBinding
import com.ggjg.presentation.ui.base.BaseActivity
import com.ggjg.presentation.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun createView() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}