package com.example.domain.entity.basket

data class MyBasketEntity(
    val id: String,
    val title: String,
    val age: Int?,
    val price: Int,
    val size: String?,
    val extraMoney: Int?,
    val unit: String,
    val isSoldOut: Boolean,
    val count: Int,
    val remainCount: Int
)