package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.request.basket.MakeBasketRequest
import com.ggjg.data.remote.response.basket.MyBasketResponse
import com.ggjg.data.remote.api.BasketAPI
import com.ggjg.library.handler.GGJGApiCall
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