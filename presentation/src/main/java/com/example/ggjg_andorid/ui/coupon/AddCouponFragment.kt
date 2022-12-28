package com.example.ggjg_andorid.ui.coupon

import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.AddCouponAdapter
import com.example.ggjg_andorid.databinding.FragmentCouponAddBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.CouponViewModel

class AddCouponFragment : BaseFragment<FragmentCouponAddBinding>(R.layout.fragment_coupon_add) {
    private lateinit var addCouponAdapter: AddCouponAdapter

    override fun onDetach() {
        super.onDetach()
        CouponViewModel.apply {
            couponList = listOf()
            currentPosition = 0
        }
    }

    override fun createView() {
        binding.addCoupon = this
        CouponViewModel.couponList = listOf("")
        initView()
    }

    private fun initView() = binding.apply {
        addCouponAdapter = AddCouponAdapter(requireActivity(), CouponViewModel.couponList).apply {
            setItemOnChangeListener(object : AddCouponAdapter.OnItemChangeListener {
                override fun changeText(item: String, index: Int) {
                    CouponViewModel.couponList = CouponViewModel.couponList.mapIndexed { i, s ->
                        if (i == index) {
                            item
                        } else {
                            s
                        }
                    }
                }
            })
        }
        addCouponList.apply {
            adapter = addCouponAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            }
            R.id.addCouponBtn -> {
                if (!CouponViewModel.couponList.contains("")) {
                    binding.addCouponScrollLayout.fullScroll(ScrollView.FOCUS_DOWN)
                    CouponViewModel.couponList = CouponViewModel.couponList.plus("")
                    CouponViewModel.currentPosition += 1
                    addCouponAdapter.changeData(CouponViewModel.couponList)
                }
                if (CouponViewModel.couponList.size == 3) {
                    binding.addCouponBtn.setVisible(false)
                }
            }
        }
    }
}