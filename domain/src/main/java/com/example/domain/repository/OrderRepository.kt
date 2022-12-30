package com.example.domain.repository

import com.example.domain.entity.order.CreateOrderEntity
import com.example.domain.entity.order.DetailOrderEntity
import com.example.domain.entity.order.InitOrderEntity
import com.example.domain.entity.order.MyOrderListEntity
import com.example.domain.param.order.BuyBreadParam

interface OrderRepository {
    suspend fun initOrderInfo(): InitOrderEntity
    suspend fun createOrder(): CreateOrderEntity
    suspend fun myOrderList(): List<MyOrderListEntity>
    suspend fun detailOrder(id: String): DetailOrderEntity
    suspend fun buyBread(buyBreadParam: BuyBreadParam)
}