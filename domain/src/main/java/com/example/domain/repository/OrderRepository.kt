package com.example.domain.repository

import com.example.domain.entity.order.InitOrderEntity

interface OrderRepository {
    suspend fun initOrderInfo(): InitOrderEntity
}