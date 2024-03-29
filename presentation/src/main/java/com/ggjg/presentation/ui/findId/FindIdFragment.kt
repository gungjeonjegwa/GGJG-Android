package com.ggjg.presentation.ui.findId

import android.view.View
import androidx.navigation.findNavController
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentFindIdBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.isEmail
import com.ggjg.presentation.utils.extension.onError
import com.ggjg.presentation.utils.extension.setOnTextChanged
import com.ggjg.presentation.utils.keyboardHide
import com.ggjg.presentation.utils.keyboardShow

class FindIdFragment : BaseFragment<FragmentFindIdBinding>(R.layout.fragment_find_id) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.findId = this
        initView()
    }

    private fun initView() = binding.apply {
        keyboardShow(requireActivity(), writeEmail)
        writeEmail.run {
            setOnTextChanged { p0, _, _, _ ->
                certifyBtn.changeActivatedWithEnabled(!p0.isNullOrBlank())
                errorEmailTxt.text = null
            }
            setOnFocusChangeListener { _, b ->
                this.isActivated = b
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().finish()
            }
            R.id.findIdLayout -> {
                keyboardHide(requireActivity(), listOf(binding.writeEmail))
            }
            R.id.certifyBtn -> {
                if (binding.writeEmail.text.toString().isEmail()) {
                    binding.errorEmailTxt.onError(
                        getString(R.string.email_wrong),
                        binding.writeEmail,
                        requireActivity()
                    )
                } else {
                    requireActivity().findNavController(R.id.findIdContainer)
                        .navigate(R.id.action_findIdFragment_to_findIdCertifyFragment)
                }
            }
        }
    }
}