package com.example.ggjg_andorid.ui.qa

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentQaBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled
import com.example.ggjg_andorid.utils.setOnTextChanged

class QAFragment: BaseFragment<FragmentQaBinding> (R.layout.fragment_qa) {
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