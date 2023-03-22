package com.ggjg.presentation.ui.findId

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentFindIdSuccessBinding
import com.ggjg.presentation.ui.base.BaseFragment

class FindIdSuccessFragment :
    BaseFragment<FragmentFindIdSuccessBinding>(R.layout.fragment_find_id_success) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.findIdSuccess = this
        (requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
            ClipData.newPlainText("ID", binding.idTxt.text.toString())
        )
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn, R.id.loginBtn -> {
                requireActivity().finish()
            }
        }
    }
}