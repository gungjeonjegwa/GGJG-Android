package com.ggjg.domain.entity.auth

data class ProfileEntity(
    val name: String,
    val coupon: Int,
    val stamp: Int,
    val deliveryWait: Int,
    val deliveryIng: Int,
    val deliveryComplete: Int,
    val cancel: Int,
    val `return`: Int,
)