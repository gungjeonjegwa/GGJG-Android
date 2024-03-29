package com.ggjg.presentation.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentAgreementBinding
import com.ggjg.presentation.ui.agree_notice.AgreementActivity
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.viewmodel.AgreementViewModel
import com.ggjg.presentation.viewmodel.RegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AgreementFragment : BottomSheetDialogFragment() {
    private val registerViewModel by activityViewModels<RegisterViewModel>()
    private lateinit var binding: FragmentAgreementBinding
    private lateinit var agreeList: List<Button>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgreementBinding.inflate(layoutInflater)
        binding.agreement = this
        agreeList = listOf(binding.agree1Btn, binding.agree2Btn, binding.agree3Btn)
        return binding.root
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.detailAgree1Btn -> {
                moveDetail(getString(R.string.agree_1_title), getString(R.string.detail_agree_1))
            }
            R.id.detailAgree2Btn -> {
                moveDetail(getString(R.string.agree_2_title), getString(R.string.detail_agree_2))
            }
            R.id.detailAgree3Btn -> {
                moveDetail(getString(R.string.agree_3_title), getString(R.string.detail_agree_3))
            }
            R.id.allAgreeBtn -> {
                view.isActivated = !view.isActivated
                agreeList.forEach {
                    it.isActivated = view.isActivated
                }
                binding.completeBtn.changeActivatedWithEnabled(view.isActivated)
            }
            R.id.agree1Btn, R.id.agree2Btn, R.id.agree3Btn -> {
                view.isActivated = !view.isActivated
                if (binding.agree1Btn.isActivated && binding.agree2Btn.isActivated) {
                    changeCompleteBtn(true)
                } else {
                    changeCompleteBtn(false)
                }
            }
            R.id.completeBtn -> {
                registerViewModel.signUp()
            }
        }
    }

    private fun changeCompleteBtn(change: Boolean) = binding.apply {
        allAgreeBtn.isActivated = change && binding.agree3Btn.isActivated
        completeBtn.changeActivatedWithEnabled(change)
    }

    private fun moveDetail(title: String, content: String) {
        AgreementViewModel.apply {
            this.title = title
            this.content = content
        }
        requireActivity().startActivity(Intent(activity, AgreementActivity::class.java))
    }
}