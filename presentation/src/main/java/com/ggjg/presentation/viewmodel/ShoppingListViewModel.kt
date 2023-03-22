package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.basket.DeleteBasketUseCase
import com.ggjg.domain.usecase.basket.MinusBasketUseCase
import com.ggjg.domain.usecase.basket.MyBasketUseCase
import com.ggjg.domain.usecase.basket.PlusBasketUseCase
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import com.ggjg.presentation.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val myBasketUseCase: MyBasketUseCase,
    private val plusBasketUseCase: PlusBasketUseCase,
    private val minusBasketUseCase: MinusBasketUseCase,
    private val deleteBasketUseCase: DeleteBasketUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var allBreadList = listOf<MyBasketEntity>()
        var selectBreadList = listOf<MyBasketEntity>()
        var allSelected = true
    }

    fun myBasket() = viewModelScope.launch {
        myBasketUseCase().onSuccess {
            allBreadList = it
            if (allSelected) {
                selectBreadList = it
            }
            event(Event.MyBasket(it))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun changeBasket(id: String, isPlus: Boolean = true) = viewModelScope.launch {
        if (isPlus) {
            plusBasketUseCase(id).onFailure {
                event(it.errorHandling(tokenErrorAction = {
                    MainViewModel.isLogin = false
                    saveTokenUseCase()
                }))
            }
        } else {
            minusBasketUseCase(id).onFailure {
                event(it.errorHandling(tokenErrorAction = {
                    MainViewModel.isLogin = false
                    saveTokenUseCase()
                }))
            }
        }
    }

    fun deleteBasket(id: String) = viewModelScope.launch {
        deleteBasketUseCase(id).onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        data class MyBasket(val data: List<MyBasketEntity>) : Event()
    }
}