package com.example.ggjg_andorid.ui.my_page

import android.animation.Animator
import android.content.Intent
import android.view.View
import android.view.animation.Animation.AnimationListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.StampAdapter
import com.example.ggjg_andorid.databinding.FragmentMyPageBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.MyPageViewModel

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val profileViewModel by activityViewModels<MyPageViewModel>()
    private lateinit var stampAdapter: StampAdapter
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

    private fun initView() = binding.apply {
        profileViewModel.profile()
        binding.myPage = this@MyPageFragment
        repeatOnStart {
            profileViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        stampAdapter = StampAdapter().apply {
            setItemOnClickListener(object : StampAdapter.OnItemClickListener {
                override fun click() {
                    presentLottie.apply {
                        setVisible()
                        addAnimatorListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(p0: Animator?) = Unit
                            override fun onAnimationRepeat(p0: Animator?) = Unit
                            override fun onAnimationCancel(p0: Animator?) = Unit
                            override fun onAnimationEnd(p0: Animator?) {
                            }
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