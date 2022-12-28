package com.example.data.remote.datasource

import com.example.data.remote.api.BasketAPI
import com.example.data.remote.request.basket.MakeBasketRequest
import com.example.data.remote.response.basket.MyBasketResponse
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class BasketDataSourceImpl @Inject constructor(
    private val basketAPI: BasketAPI,
) : BasketDataSource {
    override suspend fun myBasket(): List<MyBasketResponse> =
        HttpHandler<List<MyBasketResponse>>()
            .httpRequest { basketAPI.myBasket() }
            .sendRequest()

    override suspend fun deleteBasket(id: String) =
        HttpHandler<Unit>()
            .httpRequest { basketAPI.deleteBasket(id) }
            .sendRequest()

    override suspend fun plusBasket(id: String) =
        HttpHandler<Unit>()
            .httpRequest { basketAPI.plusBasket(id) }
            .sendRequest()

    override suspend fun minusBasket(id: String) =
        HttpHandler<Unit>()
            .httpRequest { basketAPI.minusBasket(id) }
            .sendRequest()

    override suspend fun makeBasket(makeBasketRequest: MakeBasketRequest) =
        HttpHandler<Unit>()
            .httpRequest { basketAPI.makeBasket(makeBasketRequest) }
            .sendRequest()
}