package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.order.MyOrderListEntity
import com.example.domain.usecase.order.MyOrderListUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val myOrderListUseCase: MyOrderListUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun myOrderList() = viewModelScope.launch {
        kotlin.runCatching {
            myOrderListUseCase.execute()
        }.onSuccess {
            event(Event.OrderList(it))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class OrderList(val data: List<MyOrderListEntity>) : Event()
    }
}