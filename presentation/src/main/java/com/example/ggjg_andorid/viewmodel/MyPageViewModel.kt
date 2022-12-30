package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AddressModel
import com.example.domain.usecase.auth.ChangeAddressUseCase
import com.example.domain.usecase.auth.EmailCheckUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val changeAddressUseCase: ChangeAddressUseCase,
    private val emailCheckUseCase: EmailCheckUseCase,
) : ViewModel() {
    companion object {
        var address: AddressModel? = null
    }

    private val _editEventFlow = MutableEventFlow<EditEvent>()
    val editEventFlow = _editEventFlow.asEventFlow()

    fun changeAddress() = viewModelScope.launch {
        changeAddressUseCase.execute(address!!)
    }

    fun saveInfo(email: String) = viewModelScope.launch {
        val state = emailCheckUseCase.execute(email)
        if (state.isDuplicated) {
            event(EditEvent.AlreadyEmail)
        } else {
            event(EditEvent.Success)
        }
    }

    fun event(event: EditEvent) = viewModelScope.launch {
        _editEventFlow.emit(event)
    }

    sealed class EditEvent {
        object AlreadyEmail : EditEvent()
        object Success : EditEvent()
    }
}