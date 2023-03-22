package com.ggjg.data.remote.api

import com.ggjg.data.remote.request.order.BuyBreadRequest
import com.ggjg.data.remote.response.order.CreateOrderResponse
import com.ggjg.data.remote.response.order.DetailOrderResponse
import com.ggjg.data.remote.response.order.InitOrderInfoResponse
import com.ggjg.data.remote.response.order.MyOrderListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderAPI {
    @GET("/order/list")
    suspend fun initOrderInfo(): InitOrderInfoResponse

    @POST("/order/issued")
    suspend fun createOrder(): CreateOrderResponse

    @GET("/order/user")
    suspend fun myOrderList(): List<MyOrderListResponse>

    @GET("/order/user/{orderId}")
    suspend fun detailOrder(
        @Path("orderId") id: String,
    ): DetailOrderResponse

    @POST("/order/buy")
    suspend fun buyBread(
        @Body buyBreadRequest: BuyBreadRequest,
    )
}