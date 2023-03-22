package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.request.order.BuyBreadRequest
import com.ggjg.data.remote.response.order.CreateOrderResponse
import com.ggjg.data.remote.response.order.DetailOrderResponse
import com.ggjg.data.remote.response.order.InitOrderInfoResponse
import com.ggjg.data.remote.response.order.MyOrderListResponse
import com.ggjg.data.remote.api.OrderAPI
import com.ggjg.data.utils.GGJGApiCall
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderAPI: OrderAPI,
) : OrderDataSource {
    override suspend fun initOrderInfo(): InitOrderInfoResponse = GGJGApiCall {
        orderAPI.initOrderInfo()
    }

    override suspend fun createOrder(): CreateOrderResponse = GGJGApiCall {
        orderAPI.createOrder()
    }

    override suspend fun myOrderList(): List<MyOrderListResponse> = GGJGApiCall {
        orderAPI.myOrderList()
    }

    override suspend fun detailOrder(id: String): DetailOrderResponse = GGJGApiCall {
        orderAPI.detailOrder(id = id)
    }

    override suspend fun buyBread(buyBreadRequest: BuyBreadRequest) = GGJGApiCall {
        orderAPI.buyBread(buyBreadRequest = buyBreadRequest)
    }
}