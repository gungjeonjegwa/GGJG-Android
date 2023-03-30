package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.param.auth.DeviceTokenParam
import com.ggjg.domain.usecase.auth.SaveDeviceTokenUseCase
import com.ggjg.library.handler.errorHandling
import com.ggjg.library.viewmodel.MutableEventFlow
import com.ggjg.library.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val saveDeviceTokenUseCase: SaveDeviceTokenUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun saveToken(deviceToken: String) = viewModelScope.launch {
        saveDeviceTokenUseCase(deviceTokenParam = DeviceTokenParam(deviceToken = deviceToken))
            .onSuccess {
                event(Event.Next)
            }.onFailure {
                event(Event.Next)
            }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        object Next : Event()
    }
}