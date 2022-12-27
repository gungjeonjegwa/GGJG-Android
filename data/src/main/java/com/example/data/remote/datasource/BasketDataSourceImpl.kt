package com.example.data.remote.datasource

import com.example.data.remote.api.BasketAPI
import com.example.data.remote.response.basket.MyBasketResponse
import javax.inject.Inject

class BasketDataSourceImpl @Inject constructor(
    private val basketAPI: BasketAPI,
) : BasketDataSource {
    override suspend fun myBasket(): List<MyBasketResponse> =
        basketAPI.myBasket()

    override suspend fun deleteBasket(id: String) =
        basketAPI.deleteBasket(id)

    override suspend fun plusBasket(id: String) =
        basketAPI.plusBasket(id)

    override suspend fun minusBasket(id: String) =
        basketAPI.minusBasket(id)
}