package com.ggjg.data.remote.request.basket

import com.ggjg.domain.param.basket.MakeBasketParam
import com.google.gson.annotations.SerializedName

data class MakeBasketRequest(
    @SerializedName("breadId")
    val id: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("age")
    val age: String?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("extramoney")
    val extraMoney: Int?,
    @SerializedName("unit")
    val unit: String?,
)

fun MakeBasketParam.toRequest() = MakeBasketRequest(
    id = id,
    count = count,
    age = age,
    size = size,
    extraMoney = extraMoney,
    unit = unit
)