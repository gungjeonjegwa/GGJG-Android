package com.example.ggjg_andorid.ui.shopping_list

import android.content.Intent
import android.view.View
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.entity.basket.MyBasketEntity
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.adapter.ShoppingListAdapter
import com.example.ggjg_andorid.databinding.FragmentShoppingListBinding
import com.example.ggjg_andorid.databinding.ItemShoppingListBinding
import com.example.ggjg_andorid.ui.base.BaseFragment
import com.example.ggjg_andorid.ui.login.LoginActivity
import com.example.ggjg_andorid.utils.changeActivatedWithEnabled
import com.example.ggjg_andorid.utils.repeatOnStart
import com.example.ggjg_andorid.utils.setVisible
import com.example.ggjg_andorid.viewmodel.MainViewModel
import com.example.ggjg_andorid.viewmodel.ProfileViewModel
import com.example.ggjg_andorid.viewmodel.ShoppingListViewModel
import java.text.DecimalFormat

class ShoppingListFragment :
    BaseFragment<FragmentShoppingListBinding>(R.layout.fragment_shopping_list) {
    private val shoppingListViewModel by activityViewModels<ShoppingListViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var shoppingListAdapter: ShoppingListAdapter

    override fun onDetach() {
        super.onDetach()
        ShoppingListViewModel.allSelected = true
    }

    override fun createView() {
        binding.shoppingList = this
        if (MainViewModel.isLogin) {
            shoppingListViewModel.myBasket()
            repeatOnStart {
                shoppingListViewModel.eventFlow.collect { event -> handleEvent(event) }
            }
        } else {
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
                binding.emptyShoppingListTxt.setVisible(true)
            } else {
                initView()
                shoppingListAdapter.submitList(event.data)
                binding.payBtn.setVisible()
                binding.paymentLayout.setVisible()
            }
        }
    }

    private fun initView() = binding.apply {
        mainViewModel.hiddenNav(true)
        viewTotal()
        allSelectBtn.isActivated = true
        payBtn.changeActivatedWithEnabled(true)
        shoppingListAdapter = ShoppingListAdapter().apply {
            setItemOnClickListener(object : ShoppingListAdapter.OnItemClickListener {
                override fun plus(item: MyBasketEntity) {
                    shoppingListViewModel.changeBasket(item.id)
                    ShoppingListViewModel.selectBreadList.forEach {
                        if (it == item) {
                            it.count++
                        }
                    }
                    viewTotal()
                }

                override fun minus(item: MyBasketEntity) {
                    shoppingListViewModel.changeBasket(item.id, false)
                    ShoppingListViewModel.selectBreadList.forEach {
                        if (it == item) {
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
                    } else {
                        ShoppingListViewModel.selectBreadList =
                            ShoppingListViewModel.selectBreadList.filter { it != item }
                        ShoppingListViewModel.allSelected = false
                        allSelectBtn.isActivated = false
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

            }
            R.id.allSelectBtn, R.id.allSelectTxt -> {
                binding.allSelectBtn.isActivated = !binding.allSelectBtn.isActivated
                if (binding.allSelectBtn.isActivated) {
                    ShoppingListViewModel.selectBreadList = ShoppingListViewModel.allBreadList
                    ShoppingListViewModel.allSelected = true
                } else {
                    ShoppingListViewModel.selectBreadList = listOf()
                    ShoppingListViewModel.allSelected = false
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
        }
    }
}