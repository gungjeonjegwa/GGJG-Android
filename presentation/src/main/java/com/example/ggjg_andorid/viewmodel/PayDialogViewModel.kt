package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.domain.param.basket.MakeBasketParam
import com.example.domain.usecase.auth.SaveTokenUseCase
import com.example.domain.usecase.basket.MakeBasketUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayDialogViewModel @Inject constructor(
    private val makeBasketUseCase: MakeBasketUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var breadData: DetailBreadEntity? = null
        var size: DetailBreadEntity.BreadSize? = null
        var breadList = listOf<MakeBasketParam>()
    }

    fun makeBaskets() = viewModelScope.launch {
        makeBasketUseCase(breadList).onSuccess {
            breadList = listOf()
            event(Event.SuccessMoveShoppingList)
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = { saveTokenUseCase() }))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        object SuccessMoveShoppingList : Event()
        object AlreadyShoppingList : Event()
    }
}