package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.IsLoginUseCase
import com.example.domain.usecase.auth.LogoutUseCase
import com.example.domain.usecase.auth.SaveTokenUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun logout() = viewModelScope.launch {
        kotlin.runCatching {
            logoutUseCase.execute()
        }.onSuccess {
            saveTokenUseCase.execute(null, null, null)
            MainViewModel.isLogin = false
            event(Event.Success)
        }.onFailure {
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        object Success : Event()
    }
}