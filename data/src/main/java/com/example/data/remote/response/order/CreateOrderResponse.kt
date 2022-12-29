package com.example.data.remote.response.order

import com.example.domain.entity.order.CreateOrderEntity
import com.google.gson.annotations.SerializedName

data class CreateOrderResponse(
    @SerializedName("orderId")
    val orderId: String
)

fun CreateOrderResponse.toEntity() = CreateOrderEntity(
    orderId
)
