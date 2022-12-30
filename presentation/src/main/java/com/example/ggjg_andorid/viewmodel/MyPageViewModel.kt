package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.model.AddressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : ViewModel() {
    companion object {
        var address: AddressModel? = null
    }
}