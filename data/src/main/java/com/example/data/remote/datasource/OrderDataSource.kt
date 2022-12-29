package com.example.data.remote.datasource

import com.example.data.remote.response.order.CreateOrderResponse
import com.example.data.remote.response.order.InitOrderInfoResponse

interface OrderDataSource {
    suspend fun initOrderInfo(): InitOrderInfoResponse
    suspend fun createOrder(): CreateOrderResponse
}