package com.example.data.remote.response.coupon

import com.example.domain.entity.coupon.CouponEntity
import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("myCouponId")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("finishedAt")
    val finishedAt: String,
    @SerializedName("disCountType")
    val type: String,
    @SerializedName("price")
    val price: Int,
)

fun CouponResponse.toEntity() = CouponEntity(
    id = id,
    name = name,
    createdAt = createdAt,
    finishedAt = finishedAt,
    type = type,
    price = price
)