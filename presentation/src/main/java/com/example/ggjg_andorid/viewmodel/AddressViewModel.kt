package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AddressModel
import com.example.domain.usecase.address.GetAddressUseCase
import com.example.domain.usecase.auth.RecentAddressUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val recentAddressUseCase: RecentAddressUseCase,
    private val getAddressUseCase: GetAddressUseCase,
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
                event(it.errorHandling())
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
            event(it.errorHandling())
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