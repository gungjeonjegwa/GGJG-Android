package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.param.auth.SignUpParam
import com.example.domain.usecase.auth.SignUpUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        var title = ""
        var content = ""
        var name = ""
        var phone: String? = null
        var email = ""
        var id = ""
        var pw = ""
        var rePw = ""
    }

    fun signUp() = viewModelScope.launch {
        kotlin.runCatching {
            signUpUseCase.execute(
                SignUpParam(
                    id, pw, name, phone, email
                )
            )
        }.onSuccess {
            event(Event.Success)
        }.onFailure {
        }
    }

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        object Success : Event()
    }
}