package com.ggjg.presentation.ui.agree_notice

import android.view.View
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityChangeAndRecallBinding
import com.ggjg.presentation.ui.base.BaseActivity

class ChangeAndRecallActivity: BaseActivity<ActivityChangeAndRecallBinding>(R.layout.activity_change_and_recall) {
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