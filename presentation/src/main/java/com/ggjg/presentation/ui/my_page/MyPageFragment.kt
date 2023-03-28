package com.ggjg.presentation.ui.my_page

import android.animation.Animator
import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.StampAdapter
import com.ggjg.presentation.databinding.FragmentMyPageBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.ui.login.LoginActivity
import com.ggjg.presentation.ui.toast.GGJGToast
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.MyPageViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val profileViewModel by activityViewModels<MyPageViewModel>()
    private lateinit var stampAdapter: StampAdapter
    override fun onCreate() {
        repeatOnStart {
            profileViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            profileViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        if (MainViewModel.isLogin) {
            initView()
        } else {
            this.startActivityForResult(
                Intent(
                    requireActivity(),
                    LoginActivity::class.java
                ), 0
            )
        }
    }

    private fun handleEvent(event: MyPageViewModel.Event) = when (event) {
        is MyPageViewModel.Event.Success -> {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
        is MyPageViewModel.Event.Profile -> {
            MyPageViewModel.stamp = event.data.stamp
            stampAdapter.notifyDataSetChanged()
            binding.apply {
                welcomeTxt.text = "${event.data.name}님 안녕하세요!"
                couponTxt.text = event.data.coupon.toString()
                stampSummaryTxt.text = "${event.data.stamp} / 10"
                readyDeliveryCntTxt.text = event.data.deliveryWait.toString()
                ingDeliveryCntTxt.text = event.data.deliveryIng.toString()
                finishDeliveryCntTxt.text = event.data.deliveryComplete.toString()
                cancelCntTxt.text = event.data.cancel.toString()
                recallCntTxt.text = event.data.`return`.toString()
                currentStampTxt.text = event.data.stamp.toString()
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        profileViewModel.profile()
        myPage = this@MyPageFragment
        stampAdapter = StampAdapter().apply {
            setItemOnClickListener(object : StampAdapter.OnItemClickListener {
                override fun click() {
                    profileViewModel.giftStamp()
                    presentLottie.apply {
                        setVisible()
                        addAnimatorListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(p0: Animator) {
                                Handler().postDelayed({
                                    requireActivity().findNavController(R.id.mainContainer)
                                        .navigate(R.id.action_myPageFragment_to_presentFragment)
                                }, 1700)
                            }

                            override fun onAnimationRepeat(p0: Animator) = Unit
                            override fun onAnimationCancel(p0: Animator) = Unit
                            override fun onAnimationEnd(p0: Animator) = Unit
                        })
                        playAnimation()
                    }
                }
            })
        }
        stampList.apply {
            adapter = stampAdapter
            layoutManager = GridLayoutManager(context, 5)
        }
        stampAdapter.submitList((1..10).map { it.toString() })
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.logoutBtn -> {
                profileViewModel.logout()
            }
            R.id.settingBtn -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_myPageFragment_to_myPagePrivacyFragment)
            }
            R.id.coupon, R.id.couponTxt -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_myPageFragment_to_couponFragment)
            }
            R.id.readyDeliveryLayout, R.id.ingDeliveryLayout, R.id.finishDeliveryLayout, R.id.cancelAndChangeAndRecallLayout -> {
                requireActivity().findNavController(R.id.mainContainer)
                    .navigate(R.id.action_myPageFragment_to_orderFragment)
            }
            R.id.deleteAuthBtn -> {
                GGJGToast.createText(requireContext(), "지금은 지원되지 않는 기능입니다.", false)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        } else {
            initView()
        }
    }
}