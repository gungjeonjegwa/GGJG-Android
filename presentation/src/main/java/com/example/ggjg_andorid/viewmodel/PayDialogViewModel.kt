package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.domain.exception.ConflictException
import com.example.domain.param.basket.MakeBasketParam
import com.example.domain.usecase.basket.MakeBasketUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayDialogViewModel @Inject constructor(
    private val makeBasketUseCase: MakeBasketUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        var breadData: DetailBreadEntity? = null
        var size: DetailBreadEntity.BreadSize? = null
        var breadList = listOf<MakeBasketParam>()
    }

    fun makeBaskets() = viewModelScope.launch {
        breadList.forEach { param ->
            kotlin.runCatching {
                makeBasketUseCase.execute(param)
            }.onSuccess {
                breadList = breadList.filter { it != param }
                event(Event.SuccessMoveShoppingList)
            }.onFailure {
                when (it) {
                    is ConflictException -> event(Event.AlreadyShoppingList)
                }
            }
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        object SuccessMoveShoppingList : Event()
        object AlreadyShoppingList : Event()
    }
}