package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BreadModel
import com.example.domain.usecase.bread.AllLikeBreadUseCase
import com.example.domain.usecase.bread.LikeBreadUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val allLikeBreadUseCase: AllLikeBreadUseCase,
    private val likeBreadUseCase: LikeBreadUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun getLikeBread() = viewModelScope.launch {
        allLikeBreadUseCase().onSuccess {
            if (it.isNotEmpty()) {
                event(Event.BreadList(it))
            } else {
                event(Event.Empty)
            }
        }
    }

    fun delete(id: String) = viewModelScope.launch {
        likeBreadUseCase(id)
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class BreadList(val data: List<BreadModel>) : Event()
        object Empty : Event()
    }
}