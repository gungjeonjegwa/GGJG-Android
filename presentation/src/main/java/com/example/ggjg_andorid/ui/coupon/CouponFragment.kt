package com.example.ggjg_andorid.ui.coupon

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.databinding.ActivityMainBinding
import com.example.ggjg_andorid.databinding.FragmentCouponBinding
import com.example.ggjg_andorid.ui.base.BaseActivity
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.viewmodel.MainViewModel

class CouponFragment : BaseFragment<FragmentCouponBinding>(R.layout.fragment_coupon) {
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as BaseActivity<ActivityMainBinding>).setOnKeyBackPressedListener(object :
            BaseActivity.OnKeyBackPressedListener {
            override fun onBack() {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@CouponFragment).commit()
            }
        })
    }

    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        (activity as BaseActivity<ActivityMainBinding>).deleteOnKeyBackPressedListener()
        super.onDetach()
    }

    override fun createView() {
        binding.coupon = this
        mainViewModel.hiddenNav(true)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this@CouponFragment).commit()
            }
        }
    }
}