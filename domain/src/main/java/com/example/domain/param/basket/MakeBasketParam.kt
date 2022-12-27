package com.example.domain.param.basket

data class MakeBasketParam(
    val id: String,
    var count: Int,
    val age: String?,
    val size: String?,
    val extraMoney: Int?,
    val unit: String?
)