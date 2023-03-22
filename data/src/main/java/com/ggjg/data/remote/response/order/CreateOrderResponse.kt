package com.ggjg.data.remote.response.order

import com.ggjg.domain.entity.order.CreateOrderEntity
import com.google.gson.annotations.SerializedName

data class CreateOrderResponse(
    @SerializedName("orderId")
    val orderId: String
)

fun CreateOrderResponse.toEntity() = CreateOrderEntity(
    orderId
)
