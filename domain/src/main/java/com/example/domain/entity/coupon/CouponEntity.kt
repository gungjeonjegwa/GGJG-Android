package com.example.domain.entity.coupon

data class CouponEntity(
    val id: String,
    val name: String,
    val createdAt: String,
    val finishedAt: String,
    val type: String,
    val price: Int,
)