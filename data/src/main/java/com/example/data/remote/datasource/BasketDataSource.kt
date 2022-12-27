package com.example.data.remote.datasource

import com.example.data.remote.response.basket.MyBasketResponse

interface BasketDataSource {
    suspend fun myBasket(): List<MyBasketResponse>
    suspend fun plusBasket(id: String)
}