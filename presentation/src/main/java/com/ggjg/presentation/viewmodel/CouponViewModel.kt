package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.entity.coupon.CouponEntity
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.coupon.AllCouponUseCase
import com.ggjg.domain.usecase.coupon.EnrollCouponUseCase
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import com.ggjg.presentation.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
    private val enrollCouponUseCase: EnrollCouponUseCase,
    private val allCouponUseCase: AllCouponUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {
    companion object {
        var couponList = listOf<String>()
        var currentPosition = 0
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _addEventFlow = MutableEventFlow<AddEvent>()
    val addEventFlow = _addEventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    fun allCoupon() = viewModelScope.launch {
        allCouponUseCase().onSuccess {
            event(Event.CouponList(it))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
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
                    enrollCouponUseCase(it).onFailure { error ->
                        errorCnt++
                        event(error.errorHandling(tokenErrorAction = {
                            MainViewModel.isLogin = false
                            saveTokenUseCase()
                        }))
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

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
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