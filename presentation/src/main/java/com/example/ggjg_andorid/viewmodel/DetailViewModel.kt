package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.domain.usecase.bread.DetailBreadUseCase
import com.example.domain.usecase.bread.LikeBreadUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.utils.viewmodel.errorHandling
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
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var id = ""
    }

    fun detailBread() = viewModelScope.launch {
        detailBreadUseCase(id).onSuccess {
            event(Event.DetailBread(it))
        }.onFailure {
            event(it.errorHandling())
        }
    }

    fun like() = viewModelScope.launch {
        likeBreadUseCase(id).onFailure {
            event(it.errorHandling())
        }
    }

    fun listReview() = viewModelScope.launch {
        event(Event.NoneReview)
    }

    fun listQa() = viewModelScope.launch {
        event(Event.NoneQa)
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        data class DetailBread(val detailBread: DetailBreadEntity) : Event()
        object NoneQa : Event()
        object NoneReview : Event()
        data class Review(val data: String) : Event()
        data class Qa(val data: String) : Event()
    }
}