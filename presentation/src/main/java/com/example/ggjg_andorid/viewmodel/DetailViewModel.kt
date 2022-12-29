package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.domain.usecase.auth.IsLoginUseCase
import com.example.domain.usecase.bread.DetailBreadUseCase
import com.example.domain.usecase.bread.LikeBreadUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailBreadUseCase: DetailBreadUseCase,
    private val likeBreadUseCase: LikeBreadUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    companion object {
        var id = ""
    }

    fun detailBread() = viewModelScope.launch {
        kotlin.runCatching {
            detailBreadUseCase.execute(id)
        }.onSuccess {
            event(Event.DetailBread(it))
        }.onFailure {
        }
    }

    fun like() = viewModelScope.launch {
        likeBreadUseCase.execute(id)
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class DetailBread(val detailBread: DetailBreadEntity) : Event()
    }
}