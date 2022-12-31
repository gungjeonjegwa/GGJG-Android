package com.example.ggjg_andorid.ui.coupon

import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.AddCouponAdapter
import com.example.ggjg_andorid.databinding.FragmentCouponAddBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.utils.keyboardHide
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.CouponViewModel

class AddCouponFragment : BaseFragment<FragmentCouponAddBinding>(R.layout.fragment_coupon_add) {
    private lateinit var addCouponAdapter: AddCouponAdapter
    private val couponViewModel by activityViewModels<CouponViewModel>()

    override fun onDetach() {
        CouponViewModel.apply {
            couponList = listOf()
            currentPosition = 0
        }
        super.onDetach()
    }

    override fun createView() {
        binding.addCoupon = this
        CouponViewModel.couponList = listOf("")
        initView()
        repeatOnStart {
            couponViewModel.addEventFlow.collect { event -> handleEvent(event) }
        }
    }

    private fun handleEvent(event: CouponViewModel.AddEvent) = when (event) {
        is CouponViewModel.AddEvent.NoneCoupon -> {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
        is CouponViewModel.AddEvent.WrongCoupon -> {
            keyboardHide(requireActivity(), listOf())
            Toast.makeText(context, "${event.errorCnt}개의 쿠폰 번호가 유효하지 않습니다.", Toast.LENGTH_SHORT)
                .show()
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
        is CouponViewModel.AddEvent.SuccessCoupon -> {
            keyboardHide(requireActivity(), listOf())
            Toast.makeText(context, "쿠폰 입력에 성공했습니다.", Toast.LENGTH_SHORT).show()
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        }
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
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
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
            R.id.enrollBtn -> {
                couponViewModel.enrollCoupon()
            }
        }
    }
}