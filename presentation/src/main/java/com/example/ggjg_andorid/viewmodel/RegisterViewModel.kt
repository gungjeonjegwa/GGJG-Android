package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {
    companion object {
        var title = ""
        var content = ""
        var name = ""
        var phone = ""
        var email = ""
        var id = ""
        var pw = ""
        var rePw = ""
    }
}