package com.ggjg.domain.repository

import com.ggjg.domain.entity.basket.MyBasketEntity
import com.ggjg.domain.param.basket.MakeBasketParam

interface BasketRepository {
    suspend fun myBasket(): List<MyBasketEntity>
    suspend fun deleteBasket(id: String)
    suspend fun plusBasket(id: String)
    suspend fun minusBasket(id: String)
    suspend fun makeBasket(makeBasketParam: List<MakeBasketParam>)
}