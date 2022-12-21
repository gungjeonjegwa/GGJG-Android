package com.example.ggjg_andorid.ui.splash

import android.content.Intent
import android.os.Handler
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivitySplashBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.ui.main.MainActivity

class SplashActivity: BaseActivity<ActivitySplashBinding> (R.layout.activity_splash) {
    override fun createView() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}