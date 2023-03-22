package com.example.ggjg_andorid.ui.findPw

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentFindPwNewBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.*

class FindPwNewFragment : BaseFragment<FragmentFindPwNewBinding>(R.layout.fragment_find_pw_new) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.findPwNew = this
        initView()
    }

    private fun initView() = binding.apply {
        listOf(editPw, editRePw).forEach {
            it.setOnTextChanged { p0, _, _, _ ->
                completeBtn.changeActivatedWithEnabled(!editPw.text.isNullOrBlank() && !editRePw.text.isNullOrBlank())
                when (it) {
                    editPw -> {
                        errorPwTxt.text = null
                        visibleBtn.setVisible(!p0.isNullOrBlank())
                    }
                    editRePw -> {
                        errorPwTxt.text = null
                        rePwVisibleBtn.setVisible(!p0.isNullOrBlank())
                    }
                }
            }
            it.setOnFocusChangeListener { _, b ->
                when (it) {
                    editPw -> {
                        writePw.isActivated = b
                    }
                    editRePw -> {
                        writeRePw.isActivated = b
                    }
                }
            }
        }
        keyboardShow(requireActivity(), editPw)
    }

    private fun changeVisible(editText: EditText, view: View) = editText.apply {
        if (transformationMethod == HideReturnsTransformationMethod.getInstance()) {
            transformationMethod = PasswordTransformationMethod.getInstance()
            view.setBackgroundResource(R.drawable.ic_pw_visible)
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance()
            view.setBackgroundResource(R.drawable.ic_pw_invisible)
        }
        setSelection(length())
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.findPwContainer).popBackStack()
            }
            R.id.findPwLayout -> {
                keyboardHide(requireActivity(), listOf(binding.editPw, binding.editRePw))
            }
            R.id.completeBtn -> {
                var possibleComplete = true
                if (binding.editPw.text.toString() != binding.editRePw.text.toString()) {
                    binding.errorRePwTxt.onError(
                        getString(R.string.re_pw_wrong),
                        binding.editRePw,
                        requireActivity()
                    )
                    possibleComplete = false
                }
                if (binding.editPw.text.toString().isPassword()) {
                    binding.errorPwTxt.onError(
                        getString(R.string.pw_wrong_option),
                        binding.editPw,
                        requireActivity()
                    )
                    possibleComplete = false
                }
                if (possibleComplete) {
                    requireActivity().findNavController(R.id.findPwContainer)
                        .navigate(R.id.action_findPwNewFragment_to_findPwSuccessFragment)
                }
            }
            R.id.visibleBtn -> {
                changeVisible(binding.editPw, view)
            }
            R.id.rePwVisibleBtn -> {
                changeVisible(binding.editRePw, view)
            }
        }
    }
}