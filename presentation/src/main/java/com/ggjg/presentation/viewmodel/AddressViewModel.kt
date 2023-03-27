package com.ggjg.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.model.AddressModel
import com.ggjg.domain.usecase.address.GetAddressUseCase
import com.ggjg.domain.usecase.auth.RecentAddressUseCase
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.library.event.ErrorEvent
import com.ggjg.library.handler.errorHandling
import com.ggjg.library.viewmodel.MutableEventFlow
import com.ggjg.library.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val recentAddressUseCase: RecentAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {
    private val _searchEventFlow = MutableEventFlow<SearchEvent>()
    val searchEventFlow = _searchEventFlow.asEventFlow()
    private val _changeEventFlow = MutableEventFlow<ChangeEvent>()
    val changeEventFlow = _changeEventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var currentAddress: AddressModel? = null
        var isClickSearch = false
        var isPayment = true
    }

    fun search(query: String) = viewModelScope.launch {
        getAddressUseCase(query)
            .onSuccess {
                event(SearchEvent.AddressList(it))
            }.onFailure {
                event(it.errorHandling(tokenErrorAction = {
                    MainViewModel.isLogin = false
                    saveTokenUseCase()
                }))
            }
    }

    fun recentSearch() = viewModelScope.launch {
        recentAddressUseCase().onSuccess {
            if (it.isNotEmpty()) {
                event(ChangeEvent.RecentSearch((it)))
            } else {
                event(ChangeEvent.NoSearch)
            }
        }.onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    private fun event(event: SearchEvent) = viewModelScope.launch {
        _searchEventFlow.emit(event)
    }

    private fun event(event: ChangeEvent) = viewModelScope.launch {
        _changeEventFlow.emit(event)
    }

    private fun event(event: ErrorEvent) = viewModelScope.launch {
        _errorEventFlow.emit(event)
    }

    sealed class SearchEvent {
        data class AddressList(val data: List<AddressModel>) : SearchEvent()
    }

    sealed class ChangeEvent {
        data class RecentSearch(val data: List<AddressModel>) : ChangeEvent()
        object NoSearch : ChangeEvent()
    }
}