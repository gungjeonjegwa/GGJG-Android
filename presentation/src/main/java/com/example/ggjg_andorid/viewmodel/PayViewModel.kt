package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(

) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        lateinit var breadData: DetailBreadEntity
        var size: DetailBreadEntity.BreadSize? = null
        var breadList = listOf<Bread>()
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {

    }

    data class Bread(
        val money: Int,
        val extraMoney: Int,
        val name: String,
        val unit: String,
        val size: String,
        var amount: Int,
        val age: String
    )
}