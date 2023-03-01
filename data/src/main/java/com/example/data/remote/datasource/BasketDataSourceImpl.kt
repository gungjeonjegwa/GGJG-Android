package com.example.data.remote.datasource

import com.example.data.remote.api.BasketAPI
import com.example.data.remote.request.basket.MakeBasketRequest
import com.example.data.remote.response.basket.MyBasketResponse
import com.example.data.utils.GGJGApiCall
import javax.inject.Inject

class BasketDataSourceImpl @Inject constructor(
    private val basketAPI: BasketAPI,
) : BasketDataSource {
    override suspend fun myBasket(): List<MyBasketResponse> = GGJGApiCall {
        basketAPI.myBasket()
    }

    override suspend fun deleteBasket(id: String) = GGJGApiCall {
        basketAPI.deleteBasket(id = id)
    }

    override suspend fun plusBasket(id: String) = GGJGApiCall {
        basketAPI.plusBasket(id = id)
    }

    override suspend fun minusBasket(id: String) = GGJGApiCall {
        basketAPI.minusBasket(id = id)
    }

    override suspend fun makeBasket(makeBasketRequest: List<MakeBasketRequest>) = GGJGApiCall {
        basketAPI.makeBasket(makeBasketRequest = makeBasketRequest)
    }
}