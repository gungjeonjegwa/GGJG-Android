package com.example.data.repository

import com.example.data.remote.datasource.OrderDataSource
import com.example.data.remote.response.order.toEntity
import com.example.domain.entity.order.InitOrderEntity
import com.example.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource,
) : OrderRepository {
    override suspend fun initOrderInfo(): InitOrderEntity =
        orderDataSource.initOrderInfo().toEntity()
}