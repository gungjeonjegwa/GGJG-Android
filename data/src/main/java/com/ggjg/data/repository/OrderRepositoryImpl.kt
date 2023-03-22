package com.ggjg.data.repository

import com.ggjg.data.remote.datasource.OrderDataSource
import com.ggjg.data.remote.request.order.toRequest
import com.ggjg.data.remote.response.order.toEntity
import com.ggjg.domain.entity.order.CreateOrderEntity
import com.ggjg.domain.entity.order.InitOrderEntity
import com.ggjg.domain.entity.order.MyOrderListEntity
import com.ggjg.domain.param.order.BuyBreadParam
import com.ggjg.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource,
) : OrderRepository {
    override suspend fun initOrderInfo(): InitOrderEntity =
        orderDataSource.initOrderInfo().toEntity()

    override suspend fun createOrder(): CreateOrderEntity =
        orderDataSource.createOrder().toEntity()

    override suspend fun myOrderList(): List<MyOrderListEntity> =
        orderDataSource.myOrderList().map { it.toEntity() }

    override suspend fun detailOrder(id: String) =
        orderDataSource.detailOrder(id).toEntity()

    override suspend fun buyBread(buyBreadParam: BuyBreadParam) =
        orderDataSource.buyBread(buyBreadParam.toRequest())
}