package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.auth.ProfileEntity
import com.example.domain.model.AddressModel
import com.example.domain.usecase.auth.*
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val changeAddressUseCase: ChangeAddressUseCase,
    private val emailCheckUseCase: EmailCheckUseCase,
    private val profileUseCase: ProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {
    companion object {
        var address: AddressModel? = null
        var stamp = 0
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _editEventFlow = MutableEventFlow<EditEvent>()
    val editEventFlow = _editEventFlow.asEventFlow()

    fun changeAddress() = viewModelScope.launch {
        changeAddressUseCase.execute(address!!)
    }

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

    fun profile() = viewModelScope.launch {
        kotlin.runCatching {
            profileUseCase.execute()
        }.onSuccess {
            event(Event.Profile(it))
        }
    }

    fun saveInfo(email: String) = viewModelScope.launch {
        val state = emailCheckUseCase.execute(email)
        if (state.isDuplicated) {
            event(EditEvent.AlreadyEmail)
        } else {
            event(EditEvent.Success)
        }
    }

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    fun event(event: EditEvent) = viewModelScope.launch {
        _editEventFlow.emit(event)
    }


    sealed class Event {
        object Success : Event()
        data class Profile(val data: ProfileEntity) : Event()
    }

    sealed class EditEvent {
        object AlreadyEmail : EditEvent()
        object Success : EditEvent()
    }
}