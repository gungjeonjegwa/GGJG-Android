package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.entity.order.DetailOrderEntity
import com.ggjg.domain.entity.order.MyOrderListEntity
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.order.DetailOrderUseCase
import com.ggjg.domain.usecase.order.MyOrderListUseCase
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import event.ErrorEvent
import handler.errorHandling
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val myOrderListUseCase: MyOrderListUseCase,
    private val detailOrderUseCase: DetailOrderUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _detailEventFlow = MutableEventFlow<DetailEvent>()
    val detailEventFlow = _detailEventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var id = ""
        var imgList = listOf<String>()
    }

    fun myOrderList() = viewModelScope.launch {
        myOrderListUseCase().onSuccess {
            event(Event.OrderList(it))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun detailOrder() = viewModelScope.launch {
        detailOrderUseCase(id).onSuccess {
            event(DetailEvent.DetailOrder(it))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: DetailEvent) = viewModelScope.launch {
        _detailEventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        data class OrderList(val data: List<MyOrderListEntity>) : Event()
    }

    sealed class DetailEvent {
        data class DetailOrder(val data: DetailOrderEntity) : DetailEvent()
    }
}