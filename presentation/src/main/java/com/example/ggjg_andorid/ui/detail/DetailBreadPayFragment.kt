package com.example.ggjg_andorid.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.AgeOptionAdapter
import com.example.ggjg_andorid.adapter.DetailBreadPayAdapter
import com.example.ggjg_andorid.adapter.SizeOptionAdapter
import com.example.ggjg_andorid.databinding.FragmentDetailBreadPayBinding
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.PayViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat

class DetailBreadPayFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailBreadPayBinding
    private lateinit var sizeAdapter: SizeOptionAdapter
    private lateinit var ageAdapter: AgeOptionAdapter
    private lateinit var breadPayAdapter: DetailBreadPayAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBreadPayBinding.inflate(layoutInflater)
        binding.detailBreadPay = this
        initView()
        if (PayViewModel.breadData != null) {
            sizeAdapter.submitList(PayViewModel.breadData!!.breadSize)
        }
        ageAdapter.submitList(listOf(getString(R.string.no_select)).plus((1..100).map { it.toString() }))
        return binding.root
    }

    private fun initView() = binding.apply {
        sizeAdapter = SizeOptionAdapter().apply {
            setItemOnClickListener(object : SizeOptionAdapter.OnItemClickListener {
                override fun click(item: DetailBreadEntity.BreadSize) {
                    if (PayViewModel.breadList.isEmpty()) {
                        sizeOptionBtn.text =
                            "${item.unit} ${item.size} ${if (item.extraMoney != null) "(${item.extraMoney})" else ""}"
                        sizeOptionBtn.setTextColor(requireContext().getColor(R.color.black))
                        PayViewModel.size = item
                    } else {
                        PayViewModel.breadList.forEach {
                            if (it.size != item.size) {
                                sizeOptionBtn.text =
                                    "${item.unit} ${item.size} ${if (item.extraMoney != null) "(${item.extraMoney})" else ""}"
                                binding.sizeOptionBtn.setTextColor(requireContext().getColor(R.color.black))
                                PayViewModel.size = item
                            } else {
                                Toast.makeText(context,
                                    getString(R.string.select_already_option),
                                    Toast.LENGTH_SHORT).show()
                                sizeOptionBtn.text = getString(R.string.select_option)
                                sizeOptionBtn.setTextColor(requireContext().getColor(R.color.dark_gray))
                            }
                        }
                    }
                    sizeOptionClick(true)
                }
            })
        }
        sizeOptionList.apply {
            adapter = sizeAdapter
            layoutManager = LinearLayoutManager(context)
        }
        ageAdapter = AgeOptionAdapter().apply {
            setItemOnClickListener(object : AgeOptionAdapter.OnItemClickListener {
                override fun click(item: String) {
                    ageOptionClick(true)
                    PayViewModel.breadList = PayViewModel.breadList.plus(
                        PayViewModel.Bread(
                            PayViewModel.breadData?.price?.filter { it != ',' && it != '원' }
                                ?.toInt() ?: 0,
                            PayViewModel.size!!.extraMoney?.filter { it != ',' && it != '원' }
                                ?.toInt() ?: 0,
                            PayViewModel.breadData?.name ?: "",
                            PayViewModel.size!!.unit,
                            PayViewModel.size!!.size,
                            1,
                            item
                        )
                    )
                    breadPayAdapter.submitList(PayViewModel.breadList)
                    PayViewModel.size = null
                    totalCost()
                }
            })
        }
        ageOptionList.apply {
            adapter = ageAdapter
            layoutManager = LinearLayoutManager(context)
        }
        breadPayAdapter = DetailBreadPayAdapter().apply {
            setItemOnClickListener(object : DetailBreadPayAdapter.OnItemClickListener {
                override fun plus(item: PayViewModel.Bread) {
                    PayViewModel.breadList.forEach {
                        if (it == item) {
                            it.amount++
                        }
                    }
                    breadPayAdapter.submitList(PayViewModel.breadList)
                    totalCost()
                }

                override fun minus(item: PayViewModel.Bread) {
                    PayViewModel.breadList.forEach {
                        if (it == item) {
                            it.amount--
                        }
                    }
                    breadPayAdapter.submitList(PayViewModel.breadList)
                    totalCost()
                }

                override fun delete(item: PayViewModel.Bread) {
                    PayViewModel.breadList = PayViewModel.breadList.filter { it != item }
                    breadPayAdapter.submitList(PayViewModel.breadList)
                    totalCost()
                }
            })
        }
        paymentList.apply {
            itemAnimator = null
            adapter = breadPayAdapter
            layoutManager = LinearLayoutManager(context)
        }
        if (PayViewModel.breadList.isNotEmpty()) {
            paymentLayout.setVisible()
            breadPayAdapter.submitList(PayViewModel.breadList)
            totalCost()
        }
    }

    private fun totalCost() = binding.apply {
        var totalAmount = 0
        var totalCost = 0
        PayViewModel.breadList.forEach {
            totalAmount += it.amount
            totalCost += (it.money + it.extraMoney) * it.amount
        }
        if (totalAmount == 0) {
            paymentLayout.setVisible(false)
        } else {
            totalTxt.text = "총합 ${totalAmount}개"
            costTxt.text = DecimalFormat("#,###").format(totalCost)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.closeBtn -> {
                dialog?.dismiss()
            }
            R.id.sizeOptionBtn -> {
                sizeOptionClick(false)
            }
            R.id.ageOptionBtn -> {
                if (PayViewModel.size == null) {
                    Toast.makeText(context,
                        getString(R.string.select_first_option),
                        Toast.LENGTH_SHORT).show()
                } else {
                    ageOptionClick(false)
                }
            }
        }
    }

    private fun sizeOptionClick(isVisible: Boolean) = binding.apply {
        ageOptionLayout.setVisible(isVisible)
        paymentLayout.setVisible(isVisible)
        payOptionBtnLayout.setVisible(isVisible)
        sizeOptionList.setVisible(!isVisible)
        sizeOptionLayout.isActivated = !isVisible
        if (PayViewModel.breadList.isEmpty()) {
            paymentLayout.setVisible(false)
        }
    }

    private fun ageOptionClick(isVisible: Boolean) = binding.apply {
        sizeOptionLayout.setVisible(isVisible)
        paymentLayout.setVisible(isVisible)
        payOptionBtnLayout.setVisible(isVisible)
        ageOptionList.setVisible(!isVisible)
        ageOptionLayout.isActivated = !isVisible
        if (isVisible) {
            sizeOptionBtn.text = getString(R.string.select_option)
            sizeOptionBtn.setTextColor(requireContext().getColor(R.color.dark_gray))
        }
    }
}