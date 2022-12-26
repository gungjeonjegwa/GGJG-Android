package com.example.ggjg_andorid.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.BreadEntity
import com.example.domain.usecase.bread.AllBreadUseCase
import com.example.domain.usecase.bread.CategoryBreadUseCase
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allBreadUseCase: AllBreadUseCase,
    private val categoryBreadUseCase: CategoryBreadUseCase
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        var page = 0
        var isLast = false
    }

    fun allBread() = viewModelScope.launch {
        if (!isLast) {
            kotlin.runCatching {
                allBreadUseCase.execute(page.toString(), "10")
            }.onSuccess {
                if (page == 0) {
                    event(Event.Bread(it.breadList))
                } else {
                    event(Event.AddBread(it.breadList))
                }
                isLast = it.isLast
                page++
            }
        }
    }

    fun categoryBread(view: View) = viewModelScope.launch {
        val category = when (view.id) {
            R.id.breadBtn -> "BREAD"
            R.id.cakeBtn -> "CAKE"
            R.id.cookieBtn -> "COOKIE"
            R.id.presentBtn -> "PRESENT"
            else -> "BREAD"
        }
        if (!isLast) {
            kotlin.runCatching {
                categoryBreadUseCase.execute(page.toString(), "10", category)
            }.onSuccess {
                if (page == 0) {
                    event(Event.Bread(it.breadList))
                } else {
                    event(Event.AddBread(it.breadList))
                }
                isLast = it.isLast
                page++
            }.onFailure {

            }
        }
    }

    fun getBanner() = viewModelScope.launch {
        kotlin.runCatching {
            listOf(
                "http://gungjeon.co.kr/_dj/img/main_section_1_img1.jpg",
                "http://gungjeon.co.kr/_dj/img/main_section_1_img2.jpg",
                "http://gungjeon.co.kr/_dj/img/main_section_1_img3.jpg"
            )
        }.onSuccess {
            event(Event.Banner(it))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class Banner(val bannerList: List<String>) : Event()
        data class Bread(val breadList: List<BreadEntity.Bread>) : Event()
        data class AddBread(val breadList: List<BreadEntity.Bread>) : Event()
    }
}