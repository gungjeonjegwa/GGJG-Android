package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.request.basket.MakeBasketRequest
import com.ggjg.data.remote.response.basket.MyBasketResponse

interface BasketDataSource {
    suspend fun myBasket(): List<MyBasketResponse>
    suspend fun deleteBasket(id: String)
    suspend fun plusBasket(id: String)
    suspend fun minusBasket(id: String)
    suspend fun makeBasket(makeBasketRequest: List<MakeBasketRequest>)
}