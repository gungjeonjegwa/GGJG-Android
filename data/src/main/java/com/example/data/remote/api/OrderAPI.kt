package com.example.data.remote.api

import com.example.data.remote.request.order.BuyBreadRequest
import com.example.data.remote.response.order.CreateOrderResponse
import com.example.data.remote.response.order.InitOrderInfoResponse
import com.example.data.remote.response.order.MyOrderListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderAPI {
    @GET("/order/list")
    suspend fun initOrderInfo(): InitOrderInfoResponse

    @POST("/order/issued")
    suspend fun createOrder(): CreateOrderResponse

    @GET("/order/user")
    suspend fun myOrderList(): List<MyOrderListResponse>

    @POST("/order/buy")
    suspend fun buyBread(
        @Body buyBreadRequest: BuyBreadRequest
    )
}