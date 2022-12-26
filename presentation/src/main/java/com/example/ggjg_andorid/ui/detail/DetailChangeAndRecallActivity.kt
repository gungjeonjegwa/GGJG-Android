package com.example.ggjg_andorid.ui.detail

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityChangeAndRecallBinding
import com.example.ggjg_andorid.ui.base.BaseActivity

class DetailChangeAndRecallActivity: BaseActivity<ActivityChangeAndRecallBinding>(R.layout.activity_change_and_recall) {
    override fun createView() {
        binding.detailChangeAndRecall = this
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                finish()
            }
        }
    }
}