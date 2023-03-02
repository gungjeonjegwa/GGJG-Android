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
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.utils.viewmodel.errorHandling
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
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    fun init() = viewModelScope.launch {
        initOrderInfoUseCase().onSuccess {
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
                createOrderUseCase().onSuccess { order ->
                    orderNumber = order.orderId
                }
            }
        }.onFailure {
            event(it.errorHandling())
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
        availableCouponUseCase(currentItemId!!).onSuccess {
            val data =
                it.filter { selectCouponList.find { coupon -> coupon.couponId == it.id } == null }
            event(CouponEvent.Coupon(data))
        }.onFailure {
            event(it.errorHandling())
        }
    }

    fun buyBread() = viewModelScope.launch {
        if (!address!!.isBasic) {
            newAddressUseCase(address!!).onSuccess {
                realBuy()
            }.onFailure {
                event(it.errorHandling())
            }
        } else if (defaultAddress == null) {
            changeAddressUseCase(address!!).onSuccess {
                realBuy()
            }.onFailure {
                event(it.errorHandling())
            }
        } else {
            realBuy()
        }
    }

    fun cancelBuy() = viewModelScope.launch {
        buyBreadUseCase(BuyBreadParam(
            false,
            address ?: AddressModel("", "", "", "", false),
            orderNumber!!,
            shoppingList.mapIndexed { i, data ->
                BuyBreadParam.BuyItem(
                    data.breadId,
                    data.count,
                    (data.price + (data.extraMoney ?: 0)) * data.count,
                    selectCouponList.find { it.id == i }?.discountPrice,
                    data.unit,
                    data.age,
                    selectCouponList.find { it.id == i }?.couponId
                )
            }
        )).onFailure {
            event(it.errorHandling())
        }
    }

    private fun realBuy() = viewModelScope.launch {
        buyBreadUseCase(BuyBreadParam(
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
                    selectCouponList.find { it.id == i }?.couponId
                )
            }
        )).onSuccess {
            orderNumber = null
            event(Event.SuccessPay)
        }.onFailure {
            event(it.errorHandling())
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: CouponEvent) = viewModelScope.launch {
        _couponEventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
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