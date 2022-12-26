package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.param.auth.LoginParam
import com.example.domain.usecase.auth.LoginUseCase
import com.example.domain.usecase.auth.SaveTokenUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
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

    fun login(id: String, pw: String) = viewModelScope.launch {
        kotlin.runCatching {
            loginUseCase.execute(
                LoginParam(
                    id, pw
                )
            )
        }.onSuccess {
            saveTokenUseCase.execute(it.accessToken, it.refreshToken, it.expiredAt)
            MainViewModel.isLogin = true
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