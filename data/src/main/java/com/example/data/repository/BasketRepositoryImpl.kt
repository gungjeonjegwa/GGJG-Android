package com.example.data.repository

import com.example.data.remote.datasource.BasketDataSource
import com.example.data.remote.response.basket.toEntity
import com.example.domain.entity.basket.MyBasketEntity
import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketDataSource: BasketDataSource,
) : BasketRepository {
    override suspend fun myBasket(): List<MyBasketEntity> =
        basketDataSource.myBasket().map { it.toEntity() }

    override suspend fun plusBasket(id: String) =
        basketDataSource.plusBasket(id)

    override suspend fun minusBasket(id: String) =
        basketDataSource.minusBasket(id)
}