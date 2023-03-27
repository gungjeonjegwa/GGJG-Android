package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.param.auth.SignUpParam
import com.ggjg.domain.usecase.auth.EmailCheckUseCase
import com.ggjg.domain.usecase.auth.IdCheckUseCase
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.auth.SignUpUseCase
import com.ggjg.library.event.ErrorEvent
import com.ggjg.library.handler.errorHandling
import com.ggjg.library.viewmodel.MutableEventFlow
import com.ggjg.library.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val emailCheckUseCase: EmailCheckUseCase,
    private val idCheckUseCase: IdCheckUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _registerFirstEventFlow = MutableEventFlow<RegisterFirstEvent>()
    val registerFirstEventFlow = _registerFirstEventFlow.asEventFlow()
    private val _registerSecondEventFlow = MutableEventFlow<RegisterSecondEvent>()
    val registerSecondEventFlow = _registerSecondEventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var name = ""
        var phone: String? = null
        var email = ""
        var id = ""
        var pw = ""
        var rePw = ""
    }

    fun signUp() = viewModelScope.launch {
        signUpUseCase(
            SignUpParam(
                id, pw, name, phone, email
            )
        ).onSuccess {
            event(Event.Success)
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun emailCheck(email: String) = viewModelScope.launch {
        emailCheckUseCase(email).onSuccess {
            event(RegisterFirstEvent.EmailCheck(!it.isDuplicated))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun idCheck(id: String) = viewModelScope.launch {
        idCheckUseCase(id).onSuccess {
            event(RegisterSecondEvent.IdCheck(!it.isDuplicated))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    fun event(event: RegisterFirstEvent) = viewModelScope.launch {
        _registerFirstEventFlow.emit(event)
    }

    fun event(event: RegisterSecondEvent) = viewModelScope.launch {
        _registerSecondEventFlow.emit(event)
    }

    fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        object Success : Event()
    }

    sealed class RegisterFirstEvent {
        data class EmailCheck(val state: Boolean) : RegisterFirstEvent()
    }

    sealed class RegisterSecondEvent {
        data class IdCheck(val state: Boolean) : RegisterSecondEvent()
    }
}