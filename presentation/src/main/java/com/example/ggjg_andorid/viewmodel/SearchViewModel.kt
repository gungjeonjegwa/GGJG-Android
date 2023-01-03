package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.RecentSearchEntity
import com.example.domain.entity.bread.SearchEntity
import com.example.domain.usecase.bread.DeleteRecentSearchUseCase
import com.example.domain.usecase.bread.GetRecentSearchUseCase
import com.example.domain.usecase.bread.SearchUseCase
import com.example.ggjg_andorid.adapter.RecentSearchAdapter
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    private val searchUseCase: SearchUseCase,
    private val deleteRecentSearchUseCase: DeleteRecentSearchUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _searchingEventFlow = MutableEventFlow<SearchingEvent>()
    val searchingEventFlow = _searchingEventFlow.asEventFlow()

    companion object {
        val adapter = RecentSearchAdapter()
        var search: String? = null
    }

    fun recentSearch() = viewModelScope.launch {
        kotlin.runCatching {
            getRecentSearchUseCase.execute()
        }.onSuccess {
            event(Event.RecentSearch(it))
        }
    }

    fun search() = viewModelScope.launch {
        if (!search.isNullOrBlank()) {
            kotlin.runCatching {
                searchUseCase.execute(search!!)
            }.onSuccess {
                event(SearchingEvent.Search(it))
            }
        }
    }

    fun searchResult() = viewModelScope.launch {
        if (!search.isNullOrBlank()) {
            kotlin.runCatching {

            }
        }
        event(Event.SearchResult)
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

    private fun event(event: SearchingEvent) = viewModelScope.launch {
        _searchingEventFlow.emit(event)
    }

    sealed class Event {
        data class RecentSearch(val recentSearch: List<RecentSearchEntity?>) : Event()
        object SearchResult : Event()
        object SuccessDelete : Event()
    }

    sealed class SearchingEvent {
        data class Search(val data: List<SearchEntity>) : SearchingEvent()
    }
}