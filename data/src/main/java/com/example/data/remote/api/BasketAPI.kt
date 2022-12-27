package com.example.data.remote.api

import com.example.data.remote.response.basket.MyBasketResponse
import retrofit2.http.GET

interface BasketAPI {
    @GET("/basket")
    suspend fun myBasket(): List<MyBasketResponse>
}