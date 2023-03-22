package com.ggjg.presentation.ui.qa

import android.view.View
import androidx.navigation.findNavController
import com.ggjg.presentation.R
import com.ggjg.presentation.databinding.FragmentQaBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.setOnTextChanged

class QAFragment : BaseFragment<FragmentQaBinding>(R.layout.fragment_qa) {
    override fun onCreate() {

    }

    override fun createView() {
        binding.qa = this
        initView()
    }

    private fun initView() = binding.apply {
        writeItemQuality.apply {
            setOnTextChanged { p0, _, _, _ ->
                qaBtn.changeActivatedWithEnabled(!p0.isNullOrBlank())
            }
            setOnFocusChangeListener { _, b ->
                this.isActivated = b
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
    }
}