package com.example.ggjg_andorid.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {

    private val _eventFlow = MutableEventFlow<HomeViewModel.Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun setTag(tag: View) {
        event(Event.Category(tag))
    }

    fun getBanner() = viewModelScope.launch {
        kotlin.runCatching {
            listOf(
                "http://gungjeon.co.kr/_dj/img/main_section_1_img1.jpg",
                "http://gungjeon.co.kr/_dj/img/main_section_1_img2.jpg"
            )
        }.onSuccess {
            event(Event.Banner(it))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class Category(val tag: View) : Event()
        data class Banner(val bannerList: List<String>) : Event()
    }
}