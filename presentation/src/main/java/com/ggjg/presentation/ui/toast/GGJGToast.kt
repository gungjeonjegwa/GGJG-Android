package com.ggjg.presentation.ui.toast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import coil.api.load
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ToastGgjgBinding
import com.ggjg.presentation.utils.extension.toDp

class GGJGToast {
    fun createToast(context: Context, message: String, isSuccess: Boolean): Toast {
        val inflater = LayoutInflater.from(context)
        val binding = ToastGgjgBinding.inflate(inflater)

        with(binding) {
            toastImg.load(if (isSuccess) R.drawable.ic_toast_success else R.drawable.ic_toast_fail)
            toastTxt.text = message
        }

        return Toast(context).apply {
            setGravity(Gravity.TOP or Gravity.CENTER, 0, 40.toDp())
            duration = Toast.LENGTH_LONG
            view = binding.root
        }
    }
}