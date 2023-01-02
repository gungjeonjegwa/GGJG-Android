package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.basket.MyBasketEntity
import com.example.domain.entity.coupon.CouponEntity
import com.example.domain.entity.order.InitOrderEntity
import com.example.domain.model.AddressModel
import com.example.domain.param.order.BuyBreadParam
import com.example.domain.usecase.auth.ChangeAddressUseCase
import com.example.domain.usecase.auth.NewAddressUseCase
import com.example.domain.usecase.coupon.AvailableCouponUseCase
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
    private val newAddressUseCase: NewAddressUseCase,
    private val availableCouponUseCase: AvailableCouponUseCase,
    private val changeAddressUseCase: ChangeAddressUseCase,
) : ViewModel() {
    companion object {
        var shoppingList = listOf<MyBasketEntity>()
        var payMethod: String? = null
        var orderNumber: String? = null
        var address: AddressModel? = null
        var defaultAddress: AddressModel? = null
        var selectCouponList: List<BuyCoupon> = listOf()
        var currentItemId: String? = null
        var currentItemPosition: Int = 0
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _couponEventFlow = MutableEventFlow<CouponEvent>()
    val couponEventFlow = _couponEventFlow.asEventFlow()

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

    fun availableCoupon() = viewModelScope.launch {
        kotlin.runCatching {
            availableCouponUseCase.execute(currentItemId!!)
        }.onSuccess {
            val data =
                it.filter { selectCouponList.find { coupon -> coupon.couponId == it.id } == null }
            event(CouponEvent.Coupon(data))
        }
    }

    fun buyBread() = viewModelScope.launch {
        if (!address!!.isBasic) {
            kotlin.runCatching {
                newAddressUseCase.execute(address!!)
            }.onSuccess {
                realBuy()
            }.onFailure {
            }
        } else if (defaultAddress == null) {
            kotlin.runCatching {
                changeAddressUseCase.execute(address!!)
            }.onSuccess {
                realBuy()
            }
        } else {
            realBuy()
        }
    }

    fun cancelBuy() = viewModelScope.launch {
        kotlin.runCatching {
            buyBreadUseCase.execute(BuyBreadParam(
                false,
                address!!,
                orderNumber!!,
                shoppingList.mapIndexed { i, data ->
                    BuyBreadParam.BuyItem(
                        data.breadId,
                        data.count,
                        (data.price + (data.extraMoney ?: 0)) * data.count,
                        selectCouponList.find { it.id == i }?.discountPrice,
                        data.unit,
                        data.age,
                        selectCouponList.find { it.id == i }?.couponId)
                }
            ))
        }.onFailure {
        }
    }

    private fun realBuy() = viewModelScope.launch {
        kotlin.runCatching {
            buyBreadUseCase.execute(BuyBreadParam(
                true,
                address!!,
                orderNumber!!,
                shoppingList.mapIndexed { i, data ->
                    BuyBreadParam.BuyItem(
                        data.breadId,
                        data.count,
                        (data.price + (data.extraMoney ?: 0)) * data.count,
                        selectCouponList.find { it.id == i }?.discountPrice,
                        data.unit,
                        data.age,
                        selectCouponList.find { it.id == i }?.couponId)
                }
            ))
        }.onSuccess {
            orderNumber = null
            event(Event.SuccessPay)
        }.onFailure {
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: CouponEvent) = viewModelScope.launch {
        _couponEventFlow.emit(event)
    }

    sealed class Event {
        data class InitInfo(val data: InitOrderEntity) : Event()
        data class NoAddressInitInfo(val data: InitOrderEntity) : Event()
        object SuccessPay : Event()
    }

    sealed class CouponEvent {
        data class Coupon(val data: List<CouponEntity>) : CouponEvent()
    }

    data class BuyCoupon(
        val id: Int,
        var couponId: String,
        var price: Int,
        var discountPrice: Int?,
        var type: String,
    )
}