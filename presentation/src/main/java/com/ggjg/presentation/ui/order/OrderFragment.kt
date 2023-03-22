package com.ggjg.presentation.ui.order

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.order.MyOrderListEntity
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.OrderAdapter
import com.ggjg.presentation.databinding.FragmentOrderBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.OrderViewModel

class OrderFragment : BaseFragment<FragmentOrderBinding>(R.layout.fragment_order) {
    private val orderViewModel by activityViewModels<OrderViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var orderAdapter: OrderAdapter

    override fun onDetach() {
        mainViewModel.hiddenNav(false)
        super.onDetach()
    }

    override fun onAttach(context: Context) {
        mainViewModel.hiddenNav(true)
        super.onAttach(context)
    }

    override fun onCreate() {
        repeatOnStart {
            orderViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            orderViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.order = this
        initView()
    }

    private fun handleEvent(event: OrderViewModel.Event) = when (event) {
        is OrderViewModel.Event.OrderList -> {
            orderAdapter.submitList(event.data)
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {

        }
    }

    private fun initView() = binding.apply {
        orderViewModel.myOrderList()
        orderAdapter = OrderAdapter().apply {
            setItemOnClickListener(object : OrderAdapter.OnItemClickListener {
                override fun detail(item: MyOrderListEntity) {
                    OrderViewModel.id = item.orderId
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_orderFragment_to_orderDetailFragment)
                }

                override fun cancel(item: MyOrderListEntity) {
                    Toast.makeText(context, "지금은 지원되지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
//                    requireActivity().findNavController(R.id.mainContainer)
//                        .navigate(R.id.action_orderFragment_to_orderCancelFragment)
                }

                override fun recall(item: MyOrderListEntity) {
                    Toast.makeText(context, "지금은 지원되지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
//                    requireActivity().findNavController(R.id.mainContainer)
//                        .navigate(R.id.action_orderFragment_to_orderRecallFragment)
                }

                override fun review(item: MyOrderListEntity) {
                    Toast.makeText(context, "지금은 지원되지 않는 기능입니다.", Toast.LENGTH_SHORT).show()
//                    requireActivity().findNavController(R.id.mainContainer)
//                        .navigate(R.id.action_orderFragment_to_orderReviewFragment)
                }
            })
        }
        orderList.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
        }
    }
}