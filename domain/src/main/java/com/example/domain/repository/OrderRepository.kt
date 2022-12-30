package com.example.domain.repository

import com.example.domain.entity.order.CreateOrderEntity
import com.example.domain.entity.order.InitOrderEntity
import com.example.domain.entity.order.MyOrderListEntity

interface OrderRepository {
    suspend fun initOrderInfo(): InitOrderEntity
    suspend fun createOrder(): CreateOrderEntity
    suspend fun myOrderList(): List<MyOrderListEntity>
}