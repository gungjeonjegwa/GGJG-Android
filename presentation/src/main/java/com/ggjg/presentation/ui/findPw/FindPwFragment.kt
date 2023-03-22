package com.ggjg.presentation.ui.findPw

import android.view.View
import androidx.navigation.findNavController
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentFindPwBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.isEmail
import com.ggjg.presentation.utils.extension.onError
import com.ggjg.presentation.utils.extension.setOnTextChanged
import com.ggjg.presentation.utils.keyboardHide
import com.ggjg.presentation.utils.keyboardShow

class FindPwFragment : BaseFragment<FragmentFindPwBinding>(R.layout.fragment_find_pw) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.findPw = this
        initView()
    }

    private fun initView() = binding.apply {
        keyboardShow(requireActivity(), writeId)
        listOf(writeId, writeEmail).forEach {
            it.setOnTextChanged { p0, _, _, _ ->
                certifyBtn.changeActivatedWithEnabled(!writeId.text.isNullOrBlank() && !writeEmail.text.isNullOrBlank())
                if (it == writeId) {
                    errorIdTxt.text = null
                } else {
                    errorEmailTxt.text = null
                }
            }
            it.setOnFocusChangeListener { _, b ->
                it.isActivated = b
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().finish()
            }
            R.id.findPwLayout -> {
                keyboardHide(requireActivity(), listOf(binding.writeEmail, binding.writeId))
            }
            R.id.certifyBtn -> {
                if (binding.writeEmail.text.toString().isEmail()) {
                    binding.errorEmailTxt.onError(
                        getString(R.string.email_wrong),
                        binding.writeEmail,
                        requireActivity()
                    )
                } else {
                    requireActivity().findNavController(R.id.findPwContainer)
                        .navigate(R.id.action_findPwFragment_to_findPwCertifyFragment)
                }
            }
        }
    }
}