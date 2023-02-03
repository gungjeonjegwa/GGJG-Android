package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.RecentSearchEntity
import com.example.domain.entity.bread.SearchEntity
import com.example.domain.model.BreadModel
import com.example.domain.usecase.bread.*
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
    private val resultBreadUseCase: ResultBreadUseCase,
    private val likeBreadUseCase: LikeBreadUseCase,
) : ViewModel() {
    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _searchingEventFlow = MutableEventFlow<SearchingEvent>()
    val searchingEventFlow = _searchingEventFlow.asEventFlow()
    private val _searchResultEventFlow = MutableEventFlow<SearchResultEvent>()
    val searchResultEventFlow = _searchResultEventFlow.asEventFlow()

    companion object {
        val adapter = RecentSearchAdapter()
        var search: String? = null
    }

    fun recentSearch() = viewModelScope.launch {
        getRecentSearchUseCase().onSuccess {
            event(Event.RecentSearch(it))
        }
    }

    fun search() = viewModelScope.launch {
        if (!search.isNullOrBlank()) {
            searchUseCase(search!!).onSuccess {
                event(SearchingEvent.Search(it))
            }
        }
    }

    fun like(id: String) = viewModelScope.launch {
        likeBreadUseCase(id)
    }

    fun searchResult() = viewModelScope.launch {
        event(Event.SearchResult)
        realSearch()
    }

    private fun realSearch() = viewModelScope.launch {
        if (!search.isNullOrBlank()) {
            resultBreadUseCase(search!!).onSuccess {
                event(SearchResultEvent.SearchResult(it))
            }
        }
    }

    fun deleteRecentSearch(search: String) = viewModelScope.launch {
        deleteRecentSearchUseCase(search).onSuccess {
            event(Event.SuccessDelete)
        }
    }

    private fun event(event: Event) = viewModelScope.launch {
        _eventFlow.emit(event)
    }

    private fun event(event: SearchingEvent) = viewModelScope.launch {
        _searchingEventFlow.emit(event)
    }

    private fun event(event: SearchResultEvent) = viewModelScope.launch {
        _searchResultEventFlow.emit(event)
    }

    sealed class Event {
        data class RecentSearch(val recentSearch: List<RecentSearchEntity?>) : Event()
        object SearchResult : Event()
        object SuccessDelete : Event()
    }

    sealed class SearchingEvent {
        data class Search(val data: List<SearchEntity>) : SearchingEvent()
    }

    sealed class SearchResultEvent {
        data class SearchResult(val data: List<BreadModel>) : SearchResultEvent()
    }
}