package com.ggjg.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggjg.domain.entity.bread.BannerEntity
import com.ggjg.domain.entity.bread.BreadEntity
import com.ggjg.domain.model.BreadModel
import com.ggjg.domain.usecase.auth.SaveTokenUseCase
import com.ggjg.domain.usecase.bread.AllBreadUseCase
import com.ggjg.domain.usecase.bread.BannerUseCase
import com.ggjg.domain.usecase.bread.CategoryBreadUseCase
import com.ggjg.domain.usecase.bread.LikeBreadUseCase
import com.ggjg.presentation.R
import com.ggjg.presentation.utils.viewmodel.MutableEventFlow
import com.ggjg.presentation.utils.viewmodel.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import event.ErrorEvent
import handler.errorHandling
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