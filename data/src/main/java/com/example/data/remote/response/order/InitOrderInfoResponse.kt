package com.example.data.remote.response.order

import com.example.domain.entity.order.InitOrderEntity
import com.google.gson.annotations.SerializedName

data class InitOrderInfoResponse(
    @SerializedName("address")
    val address: Address?,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
) {
    data class Address(
        @SerializedName("id")
        val id: String,
        @SerializedName("zipcode")
        val zipcode: String,
        @SerializedName("roadName")
        val road: String,
        @SerializedName("landNumber")
        val landNumber: String,
        @SerializedName("detailAddress")
        val detailAddress: String,
        @SerializedName("isBasic")
        val isBasic: Boolean,
    )
}

fun InitOrderInfoResponse.toEntity() = InitOrderEntity(
    address = address?.toEntity(),
    name = name,
    phone = phone
)

fun InitOrderInfoResponse.Address.toEntity() = InitOrderEntity.Address(
    id = id,
    zipcode = zipcode,
    road = road,
    landNumber = landNumber,
    detailAddress = detailAddress,
    isBasic = isBasic
)