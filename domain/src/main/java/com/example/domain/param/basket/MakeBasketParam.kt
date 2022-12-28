package com.example.domain.param.basket

import com.example.domain.entity.basket.MyBasketEntity

data class MakeBasketParam(
    val id: String,
    var count: Int,
    val age: String?,
    val size: String?,
    val extraMoney: Int?,
    val unit: String?
)

fun MakeBasketParam.toMyBasketEntity(title: String, img: String, price: Int) = MyBasketEntity(
    id = id,
    title = title,
    imgUrl = img,
    age = age?.toInt(),
    price = price,
    size = size,
    extraMoney = extraMoney,
    unit = unit,
    isSoldOut = false,
    count = count,
    remainCount = count
)