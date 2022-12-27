package com.example.data.remote.response.basket

import com.example.domain.entity.basket.MyBasketEntity
import com.google.gson.annotations.SerializedName

data class MyBasketResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("previewUrl")
    val imgUrl: String,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("price")
    val price: Int,
    @SerializedName("size")
    val size: String?,
    @SerializedName("extramoney")
    val extraMoney: Int?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("isSoldOut")
    val isSoldOut: Boolean,
    @SerializedName("count")
    val count: Int,
    @SerializedName("remaincount")
    val remainCount: Int
)

fun MyBasketResponse.toEntity() = MyBasketEntity(
    id = id,
    title = title,
    imgUrl = imgUrl,
    age = age,
    price = price,
    size = size,
    extraMoney = extraMoney,
    unit = unit,
    isSoldOut = isSoldOut,
    count = count,
    remainCount = remainCount
)