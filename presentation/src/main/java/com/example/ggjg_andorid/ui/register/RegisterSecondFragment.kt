package com.example.ggjg_andorid.ui.register

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentRegisterSecondBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.*
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.viewmodel.RegisterViewModel

class RegisterSecondFragment :
    BaseFragment<FragmentRegisterSecondBinding>(R.layout.fragment_register_second) {
    private val registerViewModel by activityViewModels<RegisterViewModel>()
    override fun onCreate() {
        repeatOnStart {
            registerViewModel.registerSecondEventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            registerViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.registerSecond = this
        initView()
    }

    private fun handleEvent(event: RegisterViewModel.RegisterSecondEvent) = when (event) {
        is RegisterViewModel.RegisterSecondEvent.IdCheck -> {
            if (event.state) {
                RegisterViewModel.apply {
                    id = binding.writeId.text.toString()
                    pw = binding.editPw.text.toString()
                    rePw = binding.editRePw.text.toString()
                }
                AgreementFragment().show(
                    requireActivity().supportFragmentManager,
                    "AgreementFragment"
                )
            } else {
                binding.errorIdTxt.onError(
                    getString(R.string.id_already),
                    binding.writeId,
                    requireActivity()
                )
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {}
    }

    private fun initView() = binding.apply {
        listOf(writeId, editPw, editRePw).forEach {
            it.setOnTextChanged { p0, _, _, _ ->
                okayBtn.changeActivatedWithEnabled(!writeId.text.isNullOrBlank() && !editPw.text.isNullOrBlank() && !editRePw.text.isNullOrBlank())
                when (it) {
                    editPw -> {
                        errorPwTxt.text = null
                        visibleBtn.setVisible(!p0.isNullOrBlank())
                    }
                    editRePw -> {
                        errorRePwTxt.text = null
                        rePwVisibleBtn.setVisible(!p0.isNullOrBlank())
                    }
                    writeId -> {
                        errorIdTxt.text = null
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
                    else -> {
                        it.isActivated = b
                    }
                }
            }
        }
        keyboardShow(requireActivity(), writeId)
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
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
            R.id.registerLayout -> {
                keyboardHide(
                    requireActivity(),
                    listOf(binding.writeId, binding.editPw, binding.editRePw)
                )
            }
            R.id.okayBtn -> {
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
                    registerViewModel.idCheck(binding.writeId.text.toString())
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