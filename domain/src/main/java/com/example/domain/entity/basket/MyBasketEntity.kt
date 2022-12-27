package com.example.domain.entity.basket

data class MyBasketEntity(
    val id: String,
    val title: String,
    val imgUrl: String,
    val age: Int?,
    val price: Int,
    val size: String?,
    val extraMoney: Int?,
    val unit: String?,
    val isSoldOut: Boolean,
    var count: Int,
    val remainCount: Int,
)