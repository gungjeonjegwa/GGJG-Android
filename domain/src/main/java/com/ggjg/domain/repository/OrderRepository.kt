package com.ggjg.domain.repository

import com.ggjg.domain.entity.order.CreateOrderEntity
import com.ggjg.domain.entity.order.DetailOrderEntity
import com.ggjg.domain.entity.order.InitOrderEntity
import com.ggjg.domain.entity.order.MyOrderListEntity
import com.ggjg.domain.param.order.BuyBreadParam

interface OrderRepository {
    suspend fun initOrderInfo(): InitOrderEntity
    suspend fun createOrder(): CreateOrderEntity
    suspend fun myOrderList(): List<MyOrderListEntity>
    suspend fun detailOrder(id: String): DetailOrderEntity
    suspend fun buyBread(buyBreadParam: BuyBreadParam)
}