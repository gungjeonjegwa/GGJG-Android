package com.example.domain.repository

import com.example.domain.entity.basket.MyBasketEntity
import com.example.domain.param.basket.MakeBasketParam

interface BasketRepository {
    suspend fun myBasket(): List<MyBasketEntity>
    suspend fun deleteBasket(id: String)
    suspend fun plusBasket(id: String)
    suspend fun minusBasket(id: String)
    suspend fun makeBasket(makeBasketParam: MakeBasketParam)
}