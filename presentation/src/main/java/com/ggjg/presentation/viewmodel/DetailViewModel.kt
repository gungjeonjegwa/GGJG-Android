package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.entity.bread.DetailBreadEntity
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.bread.DetailBreadUseCase
import com.ggjg.domain.usecase.bread.LikeBreadUseCase
import com.ggjg.presentation.utils.viewmodel.ErrorEvent
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import com.ggjg.presentation.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailBreadUseCase: DetailBreadUseCase,
    private val likeBreadUseCase: LikeBreadUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
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
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun like() = viewModelScope.launch {
        likeBreadUseCase(id).onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
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