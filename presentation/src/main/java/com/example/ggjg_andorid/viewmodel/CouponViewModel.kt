package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.coupon.CouponEntity
import com.example.domain.usecase.coupon.AllCouponUseCase
import com.example.domain.usecase.coupon.EnrollCouponUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    private val enrollCouponUseCase: EnrollCouponUseCase,
    private val allCouponUseCase: AllCouponUseCase,
) : ViewModel() {
    companion object {
        var couponList = listOf<String>()
        var currentPosition = 0
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _addEventFlow = MutableEventFlow<AddEvent>()
    val addEventFlow = _addEventFlow.asEventFlow()

    fun allCoupon() = viewModelScope.launch {
        allCouponUseCase().onSuccess {
            event(Event.CouponList(it))
        }
    }

    fun enrollCoupon() = viewModelScope.launch {
        var errorCnt = 0
        if (couponList.size == 1 && couponList.contains("")) {
            event(AddEvent.NoneCoupon)
        } else {
            couponList.forEach {
                if (it == "") {
                    errorCnt++
                } else {
                    enrollCouponUseCase(it).onFailure {
                        errorCnt++
                    }
                }
            }
            if (errorCnt != 0) {
                event(AddEvent.WrongCoupon(errorCnt))
            } else {
                event(AddEvent.SuccessCoupon)
            }
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: AddEvent) = viewModelScope.launch {
        _addEventFlow.emit(event)
    }

    sealed class Event {
        data class CouponList(val data: List<CouponEntity>) : Event()
    }

    sealed class AddEvent {
        data class WrongCoupon(val errorCnt: Int) : AddEvent()
        object SuccessCoupon : AddEvent()
        object NoneCoupon : AddEvent()
    }
}