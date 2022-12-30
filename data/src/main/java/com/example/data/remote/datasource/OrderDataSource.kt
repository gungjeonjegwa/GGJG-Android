package com.example.data.remote.datasource

import com.example.data.remote.request.order.BuyBreadRequest
import com.example.data.remote.response.order.CreateOrderResponse
import com.example.data.remote.response.order.DetailOrderResponse
import com.example.data.remote.response.order.InitOrderInfoResponse
import com.example.data.remote.response.order.MyOrderListResponse

interface OrderDataSource {
    suspend fun initOrderInfo(): InitOrderInfoResponse
    suspend fun createOrder(): CreateOrderResponse
    suspend fun myOrderList(): List<MyOrderListResponse>
    suspend fun detailOrder(id: String): DetailOrderResponse
    suspend fun buyBread(buyBreadRequest: BuyBreadRequest)
}