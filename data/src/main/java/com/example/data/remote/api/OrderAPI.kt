package com.example.data.remote.api

import com.example.data.remote.response.order.InitOrderInfoResponse
import retrofit2.http.GET

interface OrderAPI {
    @GET("/order/list")
    suspend fun initOrderInfo(): InitOrderInfoResponse
}