package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.model.BreadModel
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.bread.AllLikeBreadUseCase
import com.ggjg.domain.usecase.bread.LikeBreadUseCase
import com.ggjg.library.event.ErrorEvent
import com.ggjg.library.handler.errorHandling
import com.ggjg.library.viewmodel.MutableEventFlow
import com.ggjg.library.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val allLikeBreadUseCase: AllLikeBreadUseCase,
    private val likeBreadUseCase: LikeBreadUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    fun getLikeBread() = viewModelScope.launch {
        allLikeBreadUseCase().onSuccess {
            if (it.isNotEmpty()) {
                event(Event.BreadList(it))
            } else {
                event(Event.Empty)
            }
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun delete(id: String) = viewModelScope.launch {
        likeBreadUseCase(id).onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class Event {
        data class BreadList(val data: List<BreadModel>) : Event()
        object Empty : Event()
    }
}