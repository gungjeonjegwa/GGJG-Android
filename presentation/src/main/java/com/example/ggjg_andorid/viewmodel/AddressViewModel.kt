package com.example.ggjg_andorid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.removeDot
import com.example.domain.model.AddressModel
import com.example.domain.usecase.auth.RecentAddressUseCase
import com.example.ggjg_andorid.utils.MutableEventFlow
import com.example.ggjg_andorid.utils.asEventFlow
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val recentAddressUseCase: RecentAddressUseCase,
) : ViewModel() {
    private val _searchEventFlow = MutableEventFlow<SearchEvent>()
    val searchEventFlow = _searchEventFlow.asEventFlow()
    private val _changeEventFlow = MutableEventFlow<ChangeEvent>()
    val changeEventFlow = _changeEventFlow.asEventFlow()

    companion object {
        var currentAddress: AddressModel? = null
        var isClickSearch = false
        var isPayment = true
    }

    fun search(query: String) = viewModelScope.launch {
        val url =
            "https://business.juso.go.kr/addrlink/addrLinkApi.do?keyword=$query&confmKey=devU01TX0FVVEgyMDIyMTEwNzE4MDQ1MjExMzE5NTY=&resultType=json&countPerPage=100"
        val client = OkHttpClient()
        val getAddress = Request.Builder()
            .url(url)
            .build()
        client.newCall(getAddress).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonParser = JsonParser()
                val result = ((jsonParser.parse(
                    response.body?.string()
                ) as JsonObject)["results"] as JsonObject)["juso"]
                if (result.toString() != "null") {
                    var resultList = listOf<AddressModel>()
                    (result as JsonArray).forEach {
                        it as JsonObject
                        resultList = resultList.plus(
                            AddressModel(
                                "${it["zipNo"]?.toString()?.removeDot()}",
                                "${it["rn"]?.toString()?.removeDot()} ${
                                    it["buldMnnm"]?.toString()?.removeDot()
                                } ${it["roadAddrPart2"]?.toString()?.removeDot()}",
                                "${it["siNm"]?.toString()?.removeDot()} ${
                                    it["sggNm"]?.toString()?.removeDot()
                                }",
                                it["bdNm"]?.toString()?.removeDot(),
                                false
                            )
                        )
                    }
                    event(SearchEvent.AddressList(resultList))
                }
            }
        })
    }

    fun recentSearch() = viewModelScope.launch {
        kotlin.runCatching {
            recentAddressUseCase.execute()
        }.onSuccess {
            if (it.isNotEmpty()) {
                event(ChangeEvent.RecentSearch((it)))
            } else {
                event(ChangeEvent.NoSearch)
            }
        }
    }

    private fun event(event: SearchEvent) = viewModelScope.launch {
        _searchEventFlow.emit(event)
    }

    private fun event(event: ChangeEvent) = viewModelScope.launch {
        _changeEventFlow.emit(event)
    }

    sealed class SearchEvent {
        data class AddressList(val data: List<AddressModel>) : SearchEvent()
    }

    sealed class ChangeEvent {
        data class RecentSearch(val data: List<AddressModel>) : ChangeEvent()
        object NoSearch : ChangeEvent()
    }
}