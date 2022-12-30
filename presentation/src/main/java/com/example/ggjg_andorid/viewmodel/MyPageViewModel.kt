package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AddressModel
import com.example.domain.usecase.auth.ChangeAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val changeAddressUseCase: ChangeAddressUseCase
) : ViewModel() {
    companion object {
        var address: AddressModel? = null
    }

    fun changeAddress() = viewModelScope.launch {
        changeAddressUseCase.execute(address!!)
    }
}