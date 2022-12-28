package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(
) : ViewModel() {
    companion object {
        var couponList = listOf<String>()
        var currentPosition = 0
    }
}