package com.example.domain.repository

import com.example.domain.entity.basket.MyBasketEntity

interface BasketRepository {
    suspend fun myBasket(): List<MyBasketEntity>
    suspend fun deleteBasket(id: String)
    suspend fun plusBasket(id: String)
    suspend fun minusBasket(id: String)
}