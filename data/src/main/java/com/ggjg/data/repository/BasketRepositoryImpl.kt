package com.ggjg.data.repository

import com.ggjg.data.remote.datasource.BasketDataSource
import com.ggjg.data.remote.request.basket.toRequest
import com.ggjg.data.remote.response.basket.toEntity
import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.domain.param.basket.MakeBasketParam
import com.ggjg.domain.repository.BasketRepository
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketDataSource: BasketDataSource,
) : BasketRepository {
    override suspend fun myBasket(): List<MyBasketEntity> =
        basketDataSource.myBasket().map { it.toEntity() }

    override suspend fun deleteBasket(id: String) =
        basketDataSource.deleteBasket(id)

    override suspend fun plusBasket(id: String) =
        basketDataSource.plusBasket(id)

    override suspend fun minusBasket(id: String) =
        basketDataSource.minusBasket(id)

    override suspend fun makeBasket(makeBasketParam: List<MakeBasketParam>) =
        basketDataSource.makeBasket(makeBasketParam.map { it.toRequest() })
}