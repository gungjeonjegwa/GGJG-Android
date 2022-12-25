package com.example.ggjg_andorid.ui.register

import android.view.View
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentRegisterFirstBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.*
import com.example.ggjg_andorid.viewmodel.RegisterViewModel

class RegisterFirstFragment :
    BaseFragment<FragmentRegisterFirstBinding>(R.layout.fragment_register_first) {
    override fun createView() {
        binding.registerFirst = this
        initView()
    }

    private fun initView() = binding.apply {
        keyboardShow(requireActivity(), writeName)
        listOf(writeName, writePhone, writeEmail).forEach {
            it.setOnTextChanged { p0, _, _, _ ->
                nextBtn.changeActivatedWithEnabled(!writeName.text.isNullOrBlank() && !writeEmail.text.isNullOrBlank())
                if (it.id == R.id.writePhone && !binding.errorPhoneTxt.text.isNullOrBlank()) {
                    binding.errorPhoneTxt.text = null
                } else if (it.id == R.id.writeEmail && !binding.errorEmailTxt.text.isNullOrBlank()) {
                    binding.errorEmailTxt.text = null
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
            R.id.registerLayout -> {
                keyboardHide(
                    requireActivity(),
                    listOf(binding.writeName, binding.writePhone, binding.writeEmail)
                )
            }
            R.id.nextBtn -> {
                var possibleNext = true
                if (binding.writeEmail.text.toString().isEmail()) {
                    binding.errorEmailTxt.onError(
                        getString(R.string.email_wrong),
                        binding.writeEmail,
                        requireActivity()
                    )
                    possibleNext = false
                }
                if (binding.writePhone.text.toString()
                        .isPhoneNumber() && !binding.writePhone.text.isNullOrBlank()
                ) {
                    binding.errorPhoneTxt.onError(
                        getString(R.string.phone_wrong),
                        binding.writePhone,
                        requireActivity()
                    )
                    possibleNext = false
                }
                if (possibleNext) {
                    RegisterViewModel.apply {
                        name = binding.writeName.text.toString()
                        phone = binding.writePhone.text.toString()
                        email = binding.writeEmail.text.toString()
                    }
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.registerContainer, RegisterSecondFragment()).commit()
                }
            }
        }
    }
}