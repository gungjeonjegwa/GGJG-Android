package com.ggjg.presentation.ui.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.ActivityLoginBinding
import com.ggjg.presentation.ui.base.BaseActivity
import com.ggjg.presentation.ui.register.RegisterActivity
import com.ggjg.presentation.ui.toast.GGJGToast
import com.ggjg.presentation.utils.extension.*
import com.ggjg.presentation.utils.keyboardHide
import com.ggjg.presentation.utils.keyboardShow
import com.ggjg.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun createView() {
        binding.login = this
        initView()
        repeatOnStart {
            loginViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            loginViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: LoginViewModel.Event) = when (event) {
        is LoginViewModel.Event.Success -> {
            GGJGToast.createToast(this, "로그인 성공", true).show()
            setResult(1)
            finish()
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        is ErrorEvent.NotFoundError -> {
            binding.editPw.text = null
            binding.errorTxt.onError(
                getString(R.string.login_wrong),
                binding.editId,
                this,
            )
        }
        else -> {
            GGJGToast.createToast(this, "로그인 도중 오류가 발생했습니다.", false).show()
        }
    }

    private fun initView() = binding.apply {
        editId.run {
            setOnTextChanged { p0, _, _, _ ->
                deleteIdBtn.setVisible(!p0.isNullOrBlank())
                errorTxt.text = null
                loginBtn.changeActivatedWithEnabled(!p0.isNullOrBlank() && !binding.editPw.text.isNullOrBlank())
            }
            setOnFocusChangeListener { _, b ->
                writeId.isActivated = b
            }
        }
        editPw.run {
            setOnTextChanged { p0, _, _, _ ->
                visibleBtn.setVisible(!p0.isNullOrBlank())
                errorTxt.text = null
                loginBtn.changeActivatedWithEnabled(!p0.isNullOrBlank() && !binding.editId.text.isNullOrBlank())
            }
            setOnFocusChangeListener { _, b ->
                writePw.isActivated = b
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                finish()
            }
            R.id.loginLayout -> {
                keyboardHide(this, listOf(binding.editId, binding.editPw))
            }
            R.id.visibleBtn -> {
                binding.editPw.apply {
                    if (transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                        transformationMethod = PasswordTransformationMethod.getInstance()
                        view.setBackgroundResource(R.drawable.ic_pw_visible)
                    } else {
                        transformationMethod = HideReturnsTransformationMethod.getInstance()
                        view.setBackgroundResource(R.drawable.ic_pw_invisible)
                    }
                    setSelection(length())
                }
            }
            R.id.deleteIdBtn -> {
                keyboardShow(this, binding.editId)
                binding.editId.setText(null)
            }
            R.id.registerBtn -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            R.id.findIdBtn -> {
                GGJGToast.createToast(this, "지금은 지원되지 않는 기능입니다.", false).show()
//                startActivity(Intent(this, FindIdActivity::class.java))
            }
            R.id.findPwBtn -> {
                GGJGToast.createToast(this, "지금은 지원되지 않는 기능입니다.", false).show()
//                startActivity(Intent(this, FindPwActivity::class.java))
            }
            R.id.loginBtn -> {
                loginViewModel.login(binding.editId.text.toString(), binding.editPw.text.toString())
            }
        }
    }
}