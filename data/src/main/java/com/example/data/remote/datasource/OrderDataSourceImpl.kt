package com.example.data.remote.datasource

import com.example.data.remote.api.OrderAPI
import com.example.data.remote.request.order.BuyBreadRequest
import com.example.data.remote.response.order.CreateOrderResponse
import com.example.data.remote.response.order.DetailOrderResponse
import com.example.data.remote.response.order.InitOrderInfoResponse
import com.example.data.remote.response.order.MyOrderListResponse
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

    override suspend fun myOrderList(): List<MyOrderListResponse> =
        HttpHandler<List<MyOrderListResponse>>()
            .httpRequest { orderAPI.myOrderList() }
            .sendRequest()

    override suspend fun detailOrder(id: String): DetailOrderResponse =
        HttpHandler<DetailOrderResponse>()
            .httpRequest { orderAPI.detailOrder(id) }
            .sendRequest()

    override suspend fun buyBread(buyBreadRequest: BuyBreadRequest) =
        HttpHandler<Unit>()
            .httpRequest { orderAPI.buyBread(buyBreadRequest) }
            .sendRequest()
}