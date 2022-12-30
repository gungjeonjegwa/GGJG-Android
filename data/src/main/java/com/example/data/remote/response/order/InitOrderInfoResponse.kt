package com.example.data.remote.response.order

import com.example.data.remote.model.AddressModel
import com.example.data.remote.model.toEntity
import com.example.domain.entity.order.InitOrderEntity
import com.google.gson.annotations.SerializedName

data class InitOrderInfoResponse(
    @SerializedName("address")
    val address: AddressModel?,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
)

fun InitOrderInfoResponse.toEntity() = InitOrderEntity(
    address = address?.toEntity(),
    name = name,
    phone = phone
)