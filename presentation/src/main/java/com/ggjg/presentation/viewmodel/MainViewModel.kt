package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.usecase.auth.IsLoginUseCase
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import event.ErrorEvent
import handler.errorHandling
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var isLogin = false
    }

    init {
        viewModelScope.launch {
            isLoginUseCase().onSuccess {
                isLogin = it
            }.onFailure {
                event(it.errorHandling(tokenErrorAction = {
                    MainViewModel.isLogin = false
                    saveTokenUseCase()
                }))
            }
        }
    }

    fun hiddenNav(status: Boolean) {
        event(Event.IsHiddenNav(status))
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        data class IsHiddenNav(val status: Boolean) : Event()
    }
}