package com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("zipcode")
    val zipcode: String,
    @SerializedName("roadName")
    val road: String,
    @SerializedName("landNumber")
    val landNumber: String,
    @SerializedName("detailAddress")
    val detailAddress: String?,
    @SerializedName("isBasic")
    val isBasic: Boolean,
)

fun AddressModel.toEntity() = com.example.domain.model.AddressModel(
    zipcode = zipcode,
    road = road,
    landNumber = landNumber,
    detailAddress = detailAddress,
    isBasic = isBasic
)
