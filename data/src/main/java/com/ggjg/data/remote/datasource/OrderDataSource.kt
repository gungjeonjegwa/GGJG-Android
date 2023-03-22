package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.request.order.BuyBreadRequest
import com.ggjg.data.remote.response.order.CreateOrderResponse
import com.ggjg.data.remote.response.order.DetailOrderResponse
import com.ggjg.data.remote.response.order.InitOrderInfoResponse
import com.ggjg.data.remote.response.order.MyOrderListResponse

interface OrderDataSource {
    suspend fun initOrderInfo(): InitOrderInfoResponse
    suspend fun createOrder(): CreateOrderResponse
    suspend fun myOrderList(): List<MyOrderListResponse>
    suspend fun detailOrder(id: String): DetailOrderResponse
    suspend fun buyBread(buyBreadRequest: BuyBreadRequest)
}