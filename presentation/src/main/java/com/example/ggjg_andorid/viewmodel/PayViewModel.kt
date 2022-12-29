package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.basket.MyBasketEntity
import com.example.domain.entity.order.InitOrderEntity
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
) : ViewModel() {
    companion object {
        var shoppingList = listOf<MyBasketEntity>()
        var payMethod: String? = null
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun init() = viewModelScope.launch {
        kotlin.runCatching {
            initOrderInfoUseCase.execute()
        }.onSuccess {
            event(Event.InitInfo(it))
        }
    }

    fun setPayMethod(id: Int) = when (id) {
        R.id.payPhoneBtn -> payMethod = "휴대폰"
        R.id.payCardBtn -> payMethod = "카드"
        R.id.payTransferBtn -> payMethod = "계좌이체"
        R.id.payKakaoBtn -> payMethod = "카카오"
        else -> payMethod = null
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class InitInfo(val data: InitOrderEntity) : Event()
    }
}