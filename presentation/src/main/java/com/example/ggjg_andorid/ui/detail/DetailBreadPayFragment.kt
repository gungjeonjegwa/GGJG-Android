package com.example.ggjg_andorid.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.domain.param.basket.MakeBasketParam
import com.example.domain.param.basket.toMyBasketEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.AgeOptionAdapter
import com.example.ggjg_andorid.adapter.DetailBreadPayAdapter
import com.example.ggjg_andorid.adapter.SizeOptionAdapter
import com.example.ggjg_andorid.databinding.FragmentDetailBreadPayBinding
import com.example.ggjg_andorid.ui.pay.PayFragment
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.PayDialogViewModel
import com.example.ggjg_andorid.viewmodel.PayViewModel
import com.example.ggjg_andorid.viewmodel.ShoppingListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.DecimalFormat

class DetailBreadPayFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailBreadPayBinding
    private lateinit var sizeAdapter: SizeOptionAdapter
    private lateinit var ageAdapter: AgeOptionAdapter
    private lateinit var breadPayAdapter: DetailBreadPayAdapter
    private val payViewModel by activityViewModels<PayDialogViewModel>()

    override fun onDetach() {
        super.onDetach()
        PayDialogViewModel.size = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBreadPayBinding.inflate(layoutInflater)
        binding.detailBreadPay = this
        initView()
        if (!PayDialogViewModel.breadData?.breadSize.isNullOrEmpty()) {
            sizeAdapter.submitList(PayDialogViewModel.breadData!!.breadSize)
            ageAdapter.submitList(listOf(getString(R.string.no_select)).plus((1..100).map { it.toString() }))
        }
        repeatOnStart {
            payViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        return binding.root
    }

    private fun handleEvent(event: PayDialogViewModel.Event) = when (event) {
        is PayDialogViewModel.Event.AlreadyShoppingList -> {
            dialog?.dismiss()
            PayDialogViewModel.breadList = listOf()
            Toast.makeText(context, getString(R.string.already_shopping_list), Toast.LENGTH_SHORT)
                .show()
        }
        is PayDialogViewModel.Event.SuccessMoveShoppingList -> {
            dialog?.dismiss()
            PayDialogViewModel.breadList = listOf()
            Toast.makeText(context, getString(R.string.add_shopping_list), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initView() = binding.apply {
        breadPayAdapter = DetailBreadPayAdapter().apply {
            setItemOnClickListener(object : DetailBreadPayAdapter.OnItemClickListener {
                override fun plus(item: MakeBasketParam) {
                    PayDialogViewModel.breadList.forEach {
                        if (it == item) {
                            it.count++
                        }
                    }
                    breadPayAdapter.submitList(PayDialogViewModel.breadList)
                    totalCost()
                }

                override fun minus(item: MakeBasketParam) {
                    PayDialogViewModel.breadList.forEach {
                        if (it == item) {
                            it.count--
                        }
                    }
                    breadPayAdapter.submitList(PayDialogViewModel.breadList)
                    totalCost()
                }

                override fun delete(item: MakeBasketParam) {
                    PayDialogViewModel.breadList =
                        PayDialogViewModel.breadList.filter { it != item }
                    breadPayAdapter.submitList(PayDialogViewModel.breadList)
                    totalCost()
                }
            })
        }
        paymentList.apply {
            itemAnimator = null
            adapter = breadPayAdapter
            layoutManager = LinearLayoutManager(context)
        }
        if (PayDialogViewModel.breadList.isNotEmpty()) {
            paymentLayout.setVisible()
            breadPayAdapter.submitList(PayDialogViewModel.breadList)
            totalCost()
        }
        if (!PayDialogViewModel.breadData?.breadSize.isNullOrEmpty()) {
            sizeAdapter = SizeOptionAdapter().apply {
                setItemOnClickListener(object : SizeOptionAdapter.OnItemClickListener {
                    override fun click(item: DetailBreadEntity.BreadSize) {
                        if (PayDialogViewModel.breadList.isEmpty()) {
                            sizeOptionBtn.text =
                                "${item.unit} ${item.size} ${if (item.extraMoney != null) "(${item.extraMoney})" else ""}"
                            sizeOptionBtn.setTextColor(requireContext().getColor(R.color.black))
                            PayDialogViewModel.size = item
                        } else {
                            PayDialogViewModel.breadList.forEach {
                                if (it.size != item.size) {
                                    sizeOptionBtn.text =
                                        "${item.unit} ${item.size} ${if (item.extraMoney != null) "(${item.extraMoney})" else ""}"
                                    binding.sizeOptionBtn.setTextColor(requireContext().getColor(R.color.black))
                                    PayDialogViewModel.size = item
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
                        PayDialogViewModel.breadList = PayDialogViewModel.breadList.plus(
                            MakeBasketParam(
                                PayDialogViewModel.breadData!!.id,
                                1,
                                if (item == getString(R.string.no_select)) null else item,
                                PayDialogViewModel.size!!.size,
                                PayDialogViewModel.size!!.extraMoney?.filter { it != ',' && it != '원' }
                                    ?.toInt() ?: 0,
                                PayDialogViewModel.size!!.unit,
                            )
                        )
                        breadPayAdapter.submitList(PayDialogViewModel.breadList)
                        PayDialogViewModel.size = null
                        totalCost()
                    }
                })
            }
            ageOptionList.apply {
                adapter = ageAdapter
                layoutManager = LinearLayoutManager(context)
            }
        } else {
            if (PayDialogViewModel.breadList.isEmpty()) {
                PayDialogViewModel.breadList = PayDialogViewModel.breadList.plus(
                    MakeBasketParam(
                        PayDialogViewModel.breadData!!.id,
                        1,
                        null,
                        null,
                        null,
                        null,
                    )
                )
            }
            breadPayAdapter.submitList(PayDialogViewModel.breadList)
            ageOptionLayout.setVisible(false)
            sizeOptionLayout.setVisible(false)
            paymentLayout.setVisible()
            paymentList.setVisible()
            totalCost()
        }
    }

    private fun totalCost() = binding.apply {
        var totalAmount = 0
        var totalCost = 0
        PayDialogViewModel.breadList.forEach {
            totalAmount += it.count
            totalCost += (PayDialogViewModel.breadData!!.price.replace(",", "")
                .toInt() + (it.extraMoney ?: 0)) * it.count
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
                if (PayDialogViewModel.size == null) {
                    Toast.makeText(context,
                        getString(R.string.select_first_option),
                        Toast.LENGTH_SHORT).show()
                } else {
                    ageOptionClick(false)
                }
            }
            R.id.directPay -> {
                if (PayDialogViewModel.breadList.isNotEmpty()) {
                    dialog?.dismiss()
                    PayViewModel.shoppingList = PayDialogViewModel.breadList.map {
                        it.toMyBasketEntity(
                            PayDialogViewModel.breadData!!.name,
                            PayDialogViewModel.breadData!!.imgUrl,
                            PayDialogViewModel.breadData!!.price.filter { it != ',' }.toInt()
                        )
                    }
                    PayDialogViewModel.breadList = listOf()
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_detailBreadFragment_to_payFragment)
                }
            }
            R.id.addShoppingListBtn -> {
                payViewModel.makeBaskets()
            }
        }
    }

    private fun sizeOptionClick(isVisible: Boolean) = binding.apply {
        ageOptionLayout.setVisible(isVisible)
        paymentLayout.setVisible(isVisible)
        payOptionBtnLayout.setVisible(isVisible)
        sizeOptionList.setVisible(!isVisible)
        sizeOptionLayout.isActivated = !isVisible
        if (PayDialogViewModel.breadList.isEmpty()) {
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