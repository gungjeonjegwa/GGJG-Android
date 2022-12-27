package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.basket.MyBasketEntity
import com.example.domain.usecase.auth.IsLoginUseCase
import com.example.domain.usecase.basket.MyBasketUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val myBasketUseCase: MyBasketUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        var allBreadList = listOf<MyBasketEntity>()
        var selectBreadList = listOf<MyBasketEntity>()
        var allSelected = true
    }

    fun myBasket() = viewModelScope.launch {
        kotlin.runCatching {
            myBasketUseCase.execute()
        }.onSuccess {
            allBreadList = it
            selectBreadList = it
            event(Event.MyBasket(it))
        }.onFailure {
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class MyBasket(val data: List<MyBasketEntity>) : Event()
    }
}