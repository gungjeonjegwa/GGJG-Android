package com.example.ggjg_andorid.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.bread.BannerEntity
import com.example.domain.entity.bread.BreadEntity
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
    private val _category = MutableLiveData<Int?>()
    val category: LiveData<Int?> get() = _category

    companion object {
        var page = 0
        var isLast = false
    }

    fun getBread() = viewModelScope.launch {
        val category = when (_category.value) {
            R.id.breadBtn -> "BREAD"
            R.id.cakeBtn -> "CAKE"
            R.id.cookieBtn -> "COOKIE"
            R.id.presentBtn -> "PRESENT"
            else -> null
        }
        if (!isLast) {
            event(Event.Loading)
            if (category == null) {
                allBreadUseCase(page = page).onSuccess {
                    emitBread(data = it)
                }.onFailure {
                    event(it.errorHandling(tokenErrorAction = {
                        MainViewModel.isLogin = false
                        saveTokenUseCase()
                    }))
                }
            } else {
                categoryBreadUseCase(page = page, category = category).onSuccess {
                    emitBread(data = it)
                }.onFailure {
                    event(it.errorHandling(tokenErrorAction = {
                        MainViewModel.isLogin = false
                        saveTokenUseCase()
                    }))
                }
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

    fun setCategory(viewId: Int) {
        _category.value = viewId
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

    private fun emitBread(data: BreadEntity) {
        if (page == 0) {
            event(Event.Bread(data.breadList))
        } else {
            event(Event.AddBread(data.breadList))
        }
        isLast = data.isLast
        page++
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
        object Loading : Event()
    }
}