package com.example.ggjg_andorid.ui.findId

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentFindIdSuccessBinding
import com.example.ggjg_andorid.ui.base.BaseFragment

class FindIdSuccessFragment :
    BaseFragment<FragmentFindIdSuccessBinding>(R.layout.fragment_find_id_success) {
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