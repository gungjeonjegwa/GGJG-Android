package com.ggjg.presentation.ui.shopping_list

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.presentation.R
import com.ggjg.presentation.adapter.ShoppingListAdapter
import com.ggjg.presentation.databinding.FragmentShoppingListBinding
import com.ggjg.presentation.ui.base.BaseFragment
import com.ggjg.presentation.ui.login.LoginActivity
import com.ggjg.presentation.utils.extension.changeActivatedWithEnabled
import com.ggjg.presentation.utils.extension.repeatOnStart
import com.ggjg.presentation.utils.extension.setVisible
import com.ggjg.presentation.viewmodel.MainViewModel
import com.ggjg.presentation.viewmodel.PayViewModel
import com.ggjg.presentation.viewmodel.ShoppingListViewModel
import event.ErrorEvent
import java.text.DecimalFormat

class ShoppingListFragment :
    BaseFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list) {
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onDetach() {
        ShoppingListViewModel.allSelected = true
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        if (MainViewModel.isLogin) {
            shoppingListViewModel.myBasket()
        }
    }

    override fun onCreate() {
        repeatOnStart {
            shoppingListViewModel.eventFlow.collect { event -> handleEvent(event) }
        }
        repeatOnStart {
            shoppingListViewModel.errorEventFlow.collect { event -> handleEvent(event) }
        }
    }

    override fun createView() {
        binding.shoppingList = this
        if (!MainViewModel.isLogin) {
            this.startActivityForResult(
                Intent(
                    requireActivity(),
                    LoginActivity::class.java
                ), 0
            )
        }
    }

    private fun handleEvent(event: ShoppingListViewModel.Event) = when (event) {
        is ShoppingListViewModel.Event.MyBasket -> {
            if (event.data.isEmpty()) {
                mainViewModel.hiddenNav(false)
                binding.emptyShoppingListTxt.setVisible(true)
            } else {
                initView()
                shoppingListAdapter.submitList(event.data)
                binding.payBtn.setVisible()
                binding.paymentLayout.setVisible()
            }
        }
    }

    private fun handleEvent(event: ErrorEvent) = when (event) {
        else -> {}
    }

    private fun initView() = binding.apply {
        mainViewModel.hiddenNav(true)
        viewTotal()
        allSelectBtn.isActivated = ShoppingListViewModel.allSelected
        payBtn.changeActivatedWithEnabled(ShoppingListViewModel.selectBreadList.isNotEmpty())
        shoppingListAdapter = ShoppingListAdapter().apply {
            setItemOnClickListener(object : ShoppingListAdapter.OnItemClickListener {
                override fun plus(item: MyBasketEntity) {
                    shoppingListViewModel.changeBasket(item.id)
                    ShoppingListViewModel.selectBreadList.forEach {
                        if (it.id == item.id) {
                            it.count++
                        }
                    }
                    viewTotal()
                }

                override fun minus(item: MyBasketEntity) {
                    shoppingListViewModel.changeBasket(item.id, false)
                    ShoppingListViewModel.selectBreadList.forEach {
                        if (it.id == item.id) {
                            it.count--
                        }
                    }
                    viewTotal()
                }

                override fun delete(item: MyBasketEntity) {
                    ShoppingListViewModel.allBreadList =
                        ShoppingListViewModel.allBreadList.filter { it != item }
                    ShoppingListViewModel.selectBreadList =
                        ShoppingListViewModel.selectBreadList.filter { it != item }
                    shoppingListAdapter.submitList(ShoppingListViewModel.allBreadList)
                    shoppingListViewModel.deleteBasket(item.id)
                    if (ShoppingListViewModel.selectBreadList.isEmpty()) {
                        paymentLayout.setVisible(false)
                        payBtn.setVisible(false)
                        emptyShoppingListTxt.setVisible()
                        mainViewModel.hiddenNav(false)
                    } else {
                        viewTotal()
                    }
                }

                override fun check(item: MyBasketEntity, isCheck: Boolean) {
                    if (isCheck) {
                        ShoppingListViewModel.selectBreadList =
                            ShoppingListViewModel.selectBreadList.plus(item)
                        if (ShoppingListViewModel.selectBreadList.size == ShoppingListViewModel.allBreadList.size) {
                            ShoppingListViewModel.allSelected = true
                            allSelectBtn.isActivated = true
                        }
                        payBtn.changeActivatedWithEnabled(true)
                    } else {
                        ShoppingListViewModel.selectBreadList =
                            ShoppingListViewModel.selectBreadList.filter { it != item }
                        ShoppingListViewModel.allSelected = false
                        allSelectBtn.isActivated = false
                        if (ShoppingListViewModel.selectBreadList.isEmpty()) {
                            payBtn.changeActivatedWithEnabled(false)
                        }
                    }
                    viewTotal()
                }
            })
        }
        paymentList.apply {
            itemAnimator = null
            adapter = shoppingListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun viewTotal() = binding.apply {
        var totalMoney = 0
        var totalAmount = 0
        ShoppingListViewModel.selectBreadList.forEach {
            if (!it.isSoldOut) {
                totalAmount += it.count
                totalMoney += (it.price + (it.extraMoney ?: 0)) * it.count
            }
        }
        if (totalAmount == 0) {
            deliveryCostTxt.text = "0원"
        } else {
            deliveryCostTxt.text = getString(R.string.delivery_cost_default)
        }
        itemPriceTxt.text = "${totalMoney}원"
        totalTxt.text = "총합 ${totalAmount}개"
        totalCostTxt.text =
            DecimalFormat("#,###").format(totalMoney + (if (totalAmount == 0) 0 else 3000))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.backBtn -> {
                requireActivity().findNavController(R.id.mainContainer).popBackStack()
            }
            R.id.payBtn -> {
                if (ShoppingListViewModel.selectBreadList.isNotEmpty()) {
                    PayViewModel.shoppingList =
                        ShoppingListViewModel.selectBreadList.filter { !it.isSoldOut }
                    requireActivity().findNavController(R.id.mainContainer)
                        .navigate(R.id.action_shoppingListFragment_to_payFragment)
                }
            }
            R.id.allSelectBtn, R.id.allSelectTxt -> {
                binding.allSelectBtn.isActivated = !binding.allSelectBtn.isActivated
                if (binding.allSelectBtn.isActivated) {
                    ShoppingListViewModel.selectBreadList = ShoppingListViewModel.allBreadList
                    ShoppingListViewModel.allSelected = true
                    binding.payBtn.changeActivatedWithEnabled(true)
                } else {
                    ShoppingListViewModel.selectBreadList = listOf()
                    ShoppingListViewModel.allSelected = false
                    binding.payBtn.changeActivatedWithEnabled(false)
                }
                shoppingListAdapter.notifyDataSetChanged()
                viewTotal()
            }
            R.id.selectDeleteBtn -> {
                ShoppingListViewModel.allBreadList = ShoppingListViewModel.allBreadList.filter {
                    !ShoppingListViewModel.selectBreadList.contains(it)
                }
                ShoppingListViewModel.selectBreadList.forEach {
                    shoppingListViewModel.deleteBasket(it.id)
                }
                ShoppingListViewModel.selectBreadList = listOf()
                shoppingListAdapter.submitList(ShoppingListViewModel.allBreadList)
                if (ShoppingListViewModel.allBreadList.isEmpty()) {
                    binding.paymentLayout.setVisible(false)
                    binding.payBtn.setVisible(false)
                    binding.emptyShoppingListTxt.setVisible()
                    mainViewModel.hiddenNav(false)
                } else {
                    viewTotal()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) {
            requireActivity().findNavController(R.id.mainContainer).popBackStack()
        } else {
            shoppingListViewModel.myBasket()
            repeatOnStart {
                shoppingListViewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        }
    }
}