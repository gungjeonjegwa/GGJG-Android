package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.basket.MyBasketEntity
import com.example.domain.entity.order.InitOrderEntity
import com.example.domain.model.AddressModel
import com.example.domain.param.order.BuyBreadParam
import com.example.domain.usecase.order.BuyBreadUseCase
import com.example.domain.usecase.order.CreateOrderUseCase
import com.example.domain.usecase.order.InitOrderInfoUseCase
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(
    private val initOrderInfoUseCase: InitOrderInfoUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val buyBreadUseCase: BuyBreadUseCase,
) : ViewModel() {
    companion object {
        var shoppingList = listOf<MyBasketEntity>()
        var payMethod: String? = null
        var orderNumber: String? = null
        var address: AddressModel? = null
        var defaultAddress: AddressModel? = null
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun init() = viewModelScope.launch {
        kotlin.runCatching {
            initOrderInfoUseCase.execute()
        }.onSuccess {
            defaultAddress = it.address
            if (it.address == null && address == null) {
                event(Event.NoAddressInitInfo(it))
            } else {
                if (address != null) {
                    it.address = address
                } else {
                    address = it.address
                }
                event(Event.InitInfo(it))
            }
            if (orderNumber == null) {
                kotlin.runCatching {
                    createOrderUseCase.execute()
                }.onSuccess {
                    orderNumber = createOrderUseCase.execute().orderId
                }
            }
        }.onFailure {

        }
    }

    fun setPayMethod(id: Int) = when (id) {
        R.id.payPhoneBtn -> payMethod = "휴대폰"
        R.id.payCardBtn -> payMethod = "카드"
        R.id.payTransferBtn -> payMethod = "계좌이체"
        R.id.payKakaoBtn -> payMethod = "카카오페이"
        else -> payMethod = null
    }

    fun buyBread() = viewModelScope.launch {
        kotlin.runCatching {
            buyBreadUseCase.execute(BuyBreadParam(
                true,
                address!!,
                orderNumber!!,
                shoppingList.map {
                    BuyBreadParam.BuyItem(
                        it.breadId,
                        it.count,
                        (it.price + (it.extraMoney ?: 0)) * it.count,
                        it.unit,
                        it.age)
                }
            ))
        }.onSuccess {
            println("안녕 결제 성공")
        }.onFailure {
            println("안녕 $it")
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class InitInfo(val data: InitOrderEntity) : Event()
        data class NoAddressInitInfo(val data: InitOrderEntity) : Event()
    }
}