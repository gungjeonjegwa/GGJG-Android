package com.example.ggjg_andorid.ui.register

import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityRegisterBinding
import com.example.ggjg_andorid.ui.base.BaseActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    override fun createView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.registerContainer, RegisterFirstFragment()).commit()
    }
}