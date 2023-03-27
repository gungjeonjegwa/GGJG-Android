package com.ggjg.presentation.ui.my_page

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentMyPageEditBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.*
import com.ggjg.presentation.utils.keyboardShow
import com.ggjg.presentation.viewmodel.MyPageViewModel

class MyPageEditFragment : BaseFragment<FragmentMyPageEditBinding>(R.layout.fragment_my_page_edit) {
    private val myPageViewModel by activityViewModels<MyPageViewModel>()
    override fun onCreate() {
        repeatOnStart {
            myPageViewModel.editEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.myPageEdit = this
        initView()
    }

    private fun handleEvent(event: MyPageViewModel.EditEvent) = when (event) {
        is MyPageViewModel.EditEvent.AlreadyEmail -> {
            binding.errorEmailTxt.onError(
                getString(R.string.email_already),
                binding.writeEmail,
                requireActivity()
            )
        }
        is MyPageViewModel.EditEvent.Success -> {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
    }

    private fun initView() = binding.apply {
        keyboardShow(requireActivity(), writeName)
        listOf(writeName, writeEmail, writePhone).forEach {
            it.setOnTextChanged { _, _, _, _ ->
                saveBtn.changeActivatedWithEnabled(!writeName.text.isNullOrBlank() && !writeEmail.text.isNullOrBlank())
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
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.saveBtn -> {
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
                    myPageViewModel.saveInfo(binding.writeEmail.text.toString())
                }
            }
        }
    }
}