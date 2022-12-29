package com.example.data.remote.api

import com.example.data.remote.response.order.CreateOrderResponse
import com.example.data.remote.response.order.InitOrderInfoResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderAPI {
    @GET("/order/list")
    suspend fun initOrderInfo(): InitOrderInfoResponse

    @POST("/order/issued")
    suspend fun createOrder(): CreateOrderResponse
}