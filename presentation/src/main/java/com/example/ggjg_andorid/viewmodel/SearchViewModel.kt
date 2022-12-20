package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.RecentSearchEntity
import com.example.domain.usecase.search.DeleteRecentSearchUseCase
import com.example.domain.usecase.search.GetRecentSearchUseCase
import com.example.domain.usecase.search.SearchUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    private val searchUseCase: SearchUseCase,
    private val deleteRecentSearchUseCase: DeleteRecentSearchUseCase
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun recentSearch() = viewModelScope.launch {
        kotlin.runCatching {
            getRecentSearchUseCase.execute()
        }.onSuccess {
            event(Event.RecentSearch(it))
        }
    }

    fun search(search: String) = viewModelScope.launch {
        kotlin.runCatching {
            searchUseCase.execute(RecentSearchEntity(search))
        }.onSuccess {
        }
    }

    fun deleteRecentSearch(search: String) = viewModelScope.launch {
        kotlin.runCatching {
            deleteRecentSearchUseCase.execute(search)
        }.onSuccess {
            event(Event.SuccessDelete)
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    sealed class Event {
        data class RecentSearch(val recentSearch: List<RecentSearchEntity?>) : Event()
        object SuccessDelete: Event()
    }
}