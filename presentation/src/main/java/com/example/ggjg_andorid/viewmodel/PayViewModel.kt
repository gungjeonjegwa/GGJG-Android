package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.entity.basket.MyBasketEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(
): ViewModel() {
    companion object {
        var shoppingList = listOf<MyBasketEntity>()
    }
}