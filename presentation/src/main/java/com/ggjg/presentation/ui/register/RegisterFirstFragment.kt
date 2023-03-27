package com.ggjg.presentation.ui.register

import android.view.View
import androidx.fragment.app.activityViewModels
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentRegisterFirstBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.*
import com.ggjg.presentation.utils.keyboardHide
import com.ggjg.presentation.utils.keyboardShow
import com.ggjg.presentation.viewmodel.RegisterViewModel
import event.ErrorEvent

class RegisterFirstFragment :
    BaseFragment<FragmentRegisterFirstBinding>(R.layout.fragment_register_first) {
    private val registerViewModel by activityViewModels<RegisterViewModel>()
    override fun onCreate() {
        repeatOnStart {
            registerViewModel.registerFirstEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            registerViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.registerFirst = this
        initView()
    }

    private fun handleEvent(event: RegisterViewModel.RegisterFirstEvent) = when (event) {
        is RegisterViewModel.RegisterFirstEvent.EmailCheck -> {
            if (event.state) {
                RegisterViewModel.apply {
                    name = binding.writeName.text.toString()
                    phone = binding.writePhone.text.toString()
                    email = binding.writeEmail.text.toString()
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.registerContainer, RegisterSecondFragment()).commit()
            } else {
                binding.errorEmailTxt.onError(
                    getString(R.string.email_already),
                    binding.writeEmail,
                    requireActivity()
                )
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {}
    }

    private fun initView() = binding.apply {
        keyboardShow(requireActivity(), writeName)
        listOf(writeName, writePhone, writeEmail).forEach {
            it.setOnTextChanged { p0, _, _, _ ->
                nextBtn.changeActivatedWithEnabled(!writeName.text.isNullOrBlank() && !writeEmail.text.isNullOrBlank())
                if (it.id == R.id.writePhone && !errorPhoneTxt.text.isNullOrBlank()) {
                    errorPhoneTxt.text = null
                } else if (it.id == R.id.writeEmail && !errorEmailTxt.text.isNullOrBlank()) {
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
                    registerViewModel.emailCheck(binding.writeEmail.text.toString())
                }
            }
        }
    }
}