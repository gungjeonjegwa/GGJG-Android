package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.param.auth.LoginParam
import com.ggjg.domain.usecase.auth.LoginUseCase
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import com.ggjg.presentation.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    fun login(id: String, pw: String) = viewModelScope.launch {
        loginUseCase(
            LoginParam(
                id, pw
            )
        ).onSuccess {
            saveTokenUseCase(it.accessToken, it.refreshToken, it.expiredAt)
            MainViewModel.isLogin = true
            event(Event.Success)
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

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        object Success : Event()
    }
}