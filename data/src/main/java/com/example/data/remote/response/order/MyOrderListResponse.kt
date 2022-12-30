package com.example.data.remote.response.order

import com.example.domain.entity.order.MyOrderListEntity
import com.google.gson.annotations.SerializedName

data class MyOrderListResponse(
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("activityType")
    val deliveryStatus: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("previewUrl")
    val breadImg: String,
    @SerializedName("createdDate")
    val createdDate: String,
)

fun MyOrderListResponse.toEntity() = MyOrderListEntity(
    orderId = orderId,
    deliveryStatus = deliveryStatus,
    title = title,
    price = price,
    breadImg = breadImg,
    createdDate = createdDate
)