package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.entity.auth.ProfileEntity
import com.ggjg.domain.entity.auth.ProfilePrivateEntity
import com.ggjg.domain.model.AddressModel
import com.ggjg.domain.usecase.auth.*
import com.ggjg.library.event.ErrorEvent
import com.ggjg.library.handler.errorHandling
import com.ggjg.library.viewmodel.MutableEventFlow
import com.ggjg.library.viewmodel.asEventFlow
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
    private val profilePrivateUseCase: ProfilePrivateUseCase,
    private val giftStampUseCase: GiftStampUseCase,
) : ViewModel() {
    companion object {
        var stamp = 0
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _editEventFlow = MutableEventFlow<EditEvent>()
    val editEventFlow = _editEventFlow.asEventFlow()
    private val _privacyEventFlow = MutableEventFlow<PrivacyEvent>()
    val privacyEventFlow = _privacyEventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    fun changeAddress() = viewModelScope.launch {
        changeAddressUseCase(
            AddressModel(
                PayViewModel.address!!.zipcode,
                PayViewModel.address!!.road,
                PayViewModel.address!!.landNumber,
                PayViewModel.address!!.detailAddress,
                true
            )
        ).onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
        PayViewModel.defaultAddress = PayViewModel.address
        profilePrivate()
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase().onSuccess {
            saveTokenUseCase(null, null, null)
            MainViewModel.isLogin = false
            event(Event.Success)
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun profile() = viewModelScope.launch {
        profileUseCase().onSuccess {
            event(Event.Profile(it))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun profilePrivate() = viewModelScope.launch {
        profilePrivateUseCase().onSuccess {
            event(PrivacyEvent.ProfileData(it))
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun saveInfo(email: String) = viewModelScope.launch {
        emailCheckUseCase(email).onSuccess {
            if (it.isDuplicated) {
                event(EditEvent.AlreadyEmail)
            } else {
                event(EditEvent.Success)
            }
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun giftStamp() = viewModelScope.launch {
        giftStampUseCase().onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
        stamp = 0
    }

    fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    fun event(event: EditEvent) = viewModelScope.launch {
        _editEventFlow.emit(event)
    }

    fun event(event: PrivacyEvent) = viewModelScope.launch {
        _privacyEventFlow.emit(event)
    }

    fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        object Success : Event()
        data class Profile(val data: ProfileEntity) : Event()
    }

    sealed class EditEvent {
        object AlreadyEmail : EditEvent()
        object Success : EditEvent()
    }

    sealed class PrivacyEvent {
        data class ProfileData(val data: ProfilePrivateEntity) : PrivacyEvent()
    }
}