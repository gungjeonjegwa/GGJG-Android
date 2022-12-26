package com.example.ggjg_andorid.ui.findId

import android.view.View
import androidx.navigation.findNavController
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.FragmentFindIdCertifyBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.*
import java.util.Timer
import kotlin.concurrent.timer

class FindIdCertifyFragment :
    BaseFragment<FragmentFindIdCertifyBinding>(R.layout.fragment_find_id_certify) {
    private val certifyTime = 300
    private lateinit var timer: Timer
    private var second = certifyTime % 60
    private var minute = certifyTime / 60
    override fun createView() {
        binding.findIdCertify = this
        initView()
    }

    private fun initView() = binding.apply {
        keyboardShow(requireActivity(), writeCertify)
        writeCertify.run {
            setOnTextChanged { p0, _, _, _ ->
                okayBtn.changeActivatedWithEnabled(!p0.isNullOrBlank())
                errorCertifyTxt.visibility = View.GONE
                errorCertifyTxt.text = null
            }
            setOnFocusChangeListener { _, b ->
                this.isActivated = b
            }
        }
        setTimer()
    }

    private fun setTimer() = binding.apply {
        second = certifyTime % 60
        minute = certifyTime / 60
        timeTxt.text = String.format("0$minute : %02d", second)
        timer = timer(period = 1000, initialDelay = 1000) {
            requireActivity().runOnUiThread {
                timeTxt.text = String.format("0$minute : %02d", second)
            }
            if (second == 0 && minute == 0) {
                cancel()
                requireActivity().runOnUiThread {
                    timeTxt.text = "00 : 00"
                }
                return@timer
            }
            if (second == 0) {
                minute--
                second = 60
            }
            second--
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                timer.cancel()
                requireActivity().findNavController(R.id.findIdContainer).popBackStack()
            }
            R.id.findIdLayout -> {
                keyboardHide(requireActivity(), listOf(binding.writeCertify))
            }
            R.id.okayBtn -> {
                if (second == 0 && minute == 0) {
                    binding.errorCertifyTxt.visibility = View.VISIBLE
                    binding.errorCertifyTxt.onError(
                        getString(R.string.certify_expired),
                        binding.writeCertify,
                        requireActivity()
                    )
                } else {
                    timer.cancel()
                    requireActivity().findNavController(R.id.findIdContainer)
                        .navigate(R.id.action_findIdCertifyFragment_to_findIdSuccessFragment)
                }
            }
            R.id.reSendBtn -> {
                timer.cancel()
                setTimer()
            }
        }
    }
}