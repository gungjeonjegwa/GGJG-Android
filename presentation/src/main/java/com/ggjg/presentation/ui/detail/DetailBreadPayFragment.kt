package com.ggjg.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.bread.DetailBreadEntity
import com.ggjg.domain.param.basket.MakeBasketParam
import com.ggjg.domain.param.basket.toMyBasketEntity
import com.ggjg.library.event.ErrorEvent
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.AgeOptionAdapter
import com.ggjg.presentation.adapter.DetailBreadPayAdapter
import com.ggjg.presentation.adapter.SizeOptionAdapter
import com.ggjg.presentation.databinding.FragmentDetailBreadPayBinding
import com.ggjg.presentation.ui.toast.GGJGToast
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.PayDialogViewModel
import com.ggjg.presentation.viewmodel.PayViewModel
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
        repeatOnStart {
            payViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
        return binding.root
    }

    private fun handleEvent(event: PayDialogViewModel.Event) = when (event) {
        is PayDialogViewModel.Event.SuccessMoveShoppingList -> {
            dialog?.dismiss()
            PayDialogViewModel.breadList = listOf()
            GGJGToast.createText(requireContext(), getString(R.string.add_shopping_list), true)
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        is ErrorEvent.ConflictError -> {
            dialog?.dismiss()
            PayDialogViewModel.breadList = listOf()
            GGJGToast.createText(
                requireContext(),
                getString(R.string.already_shopping_list),
                false
            )
        }
        else -> {

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
                                    GGJGToast.createText(
                                        requireContext(),
                                        getString(R.string.select_already_option),
                                        false
                                    )
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
                    GGJGToast.createText(
                        requireContext(),
                        getString(R.string.select_first_option),
                        false
                    )
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
                            PayDialogViewModel.breadData!!.price.filter { it != ',' }.toInt(),
                            PayDialogViewModel.breadData!!.id
                        )
                    }
                    PayDialogViewModel.breadList = listOf()
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_detailBreadFragment_to_payFragment)
                }
            }
            R.id.addShoppingListBtn -> {
                if (PayDialogViewModel.breadList.isNotEmpty()) {
                    payViewModel.makeBaskets()
                } else {
                    GGJGToast.createText(requireContext(), "상품을 먼저 선택해주세요", false)
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