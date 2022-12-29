package com.example.data.remote.datasource

import com.example.data.remote.api.OrderAPI
import com.example.data.remote.response.order.CreateOrderResponse
import com.example.data.remote.response.order.InitOrderInfoResponse
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderAPI: OrderAPI,
) : OrderDataSource {
    override suspend fun initOrderInfo(): InitOrderInfoResponse =
        HttpHandler<InitOrderInfoResponse>()
            .httpRequest { orderAPI.initOrderInfo() }
            .sendRequest()

    override suspend fun createOrder(): CreateOrderResponse =
        HttpHandler<CreateOrderResponse>()
            .httpRequest { orderAPI.createOrder() }
            .sendRequest()
}