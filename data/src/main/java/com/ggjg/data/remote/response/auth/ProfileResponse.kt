package com.ggjg.data.remote.response.auth

import com.ggjg.domain.entity.auth.ProfileEntity
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("couponCount")
    val coupon: Int,
    @SerializedName("stampCount")
    val stamp: Int,
    @SerializedName("waitOrderCount")
    val deliveryWait: Int,
    @SerializedName("inDeliverCount")
    val deliveryIng: Int,
    @SerializedName("completeDelivery")
    val deliveryComplete: Int,
    @SerializedName("cancel")
    val cancel: Int,
    @SerializedName("return")
    val `return`: Int,
)

fun ProfileResponse.toEntity() = ProfileEntity(
    name = name,
    coupon = coupon,
    stamp = stamp,
    deliveryWait = deliveryWait,
    deliveryIng = deliveryIng,
    deliveryComplete = deliveryComplete,
    cancel = cancel,
    `return` = `return`
)