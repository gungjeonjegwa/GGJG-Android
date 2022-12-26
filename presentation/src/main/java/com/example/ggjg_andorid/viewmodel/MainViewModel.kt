package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.IsLoginUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isLoginUseCase: IsLoginUseCase
): ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        var isLogin = false
    }

    init {
        viewModelScope.launch {
            isLogin = isLoginUseCase.execute()
        }
    }

    fun hiddenNav(status: Boolean) {
        event(Event.IsHiddenNav(status))
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }
    sealed class Event {
        data class IsHiddenNav(val status: Boolean): Event()
    }
}