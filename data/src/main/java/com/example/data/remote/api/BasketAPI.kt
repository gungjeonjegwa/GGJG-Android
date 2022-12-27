package com.example.data.remote.api

import com.example.data.remote.response.basket.MyBasketResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface BasketAPI {
    @GET("/basket")
    suspend fun myBasket(): List<MyBasketResponse>
    @PATCH("/basket/plus/{id}")
    suspend fun plusBasket(
        @Path("id") id: String
    )
}