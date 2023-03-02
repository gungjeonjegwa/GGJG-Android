package com.example.ggjg_andorid.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.BannerEntity
import com.example.domain.exception.TokenErrorException
import com.example.domain.model.BreadModel
import com.example.domain.usecase.auth.SaveTokenUseCase
import com.example.domain.usecase.bread.AllBreadUseCase
import com.example.domain.usecase.bread.BannerUseCase
import com.example.domain.usecase.bread.CategoryBreadUseCase
import com.example.domain.usecase.bread.LikeBreadUseCase
import com.example.ggjg_andorid.R
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import com.example.ggjg_andorid.utils.viewmodel.ErrorEvent
import com.example.ggjg_andorid.utils.viewmodel.errorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val allBreadUseCase: AllBreadUseCase,
    private val categoryBreadUseCase: CategoryBreadUseCase,
    private val likeBreadUseCase: LikeBreadUseCase,
    private val bannerUseCase: BannerUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) : ViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()
    private val _errorEventFlow = MutableEventFlow<ErrorEvent>()
    val errorEventFlow = _errorEventFlow.asEventFlow()

    companion object {
        var page = 0
        var isLast = false
    }

    fun allBread() = viewModelScope.launch {
        if (!isLast) {
            allBreadUseCase(page.toString(), "10").onSuccess {
                if (page == 0) {
                    event(Event.Bread(it.breadList))
                } else {
                    event(Event.AddBread(it.breadList))
                }
                isLast = it.isLast
                page++
            }.onFailure {
                event(it.errorHandling(tokenErrorAction = {
                    MainViewModel.isLogin = false
                    saveTokenUseCase()
                }))
            }
        }
    }

    fun categoryBread(view: View) = viewModelScope.launch {
        val category = when (view.id) {
            R.id.breadBtn -> "BREAD"
            R.id.cakeBtn -> "CAKE"
            R.id.cookieBtn -> "COOKIE"
            R.id.presentBtn -> "PRESENT"
            else -> "BREAD"
        }
        if (!isLast) {
            categoryBreadUseCase(page.toString(), "10", category).onSuccess {
                if (page == 0) {
                    event(Event.Bread(it.breadList))
                } else {
                    event(Event.AddBread(it.breadList))
                }
                isLast = it.isLast
                page++
            }.onFailure {
                event(it.errorHandling(tokenErrorAction = {
                    MainViewModel.isLogin = false
                    saveTokenUseCase()
                }))
            }
        }
    }

    fun like(id: String) = viewModelScope.launch {
        likeBreadUseCase(id).onFailure {
            event(it.errorHandling(tokenErrorAction = {
                MainViewModel.isLogin = false
                saveTokenUseCase()
            }))
        }
    }

    fun getBanner() = viewModelScope.launch {
        bannerUseCase().onSuccess {
            event(Event.Banner(it))
        }.onFailure {
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
        data class Banner(val bannerList: List<BannerEntity>) : Event()
        data class Bread(val breadList: List<BreadModel>) : Event()
        data class AddBread(val breadList: List<BreadModel>) : Event()
    }
}