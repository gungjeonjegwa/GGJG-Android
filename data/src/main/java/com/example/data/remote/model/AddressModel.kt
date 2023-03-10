package com.example.data.remote.model

import com.google.gson.annotations.SerializedName

data class AddressModel(
    @SerializedName("zipCode")
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

fun com.example.domain.model.AddressModel.toRequest() = AddressModel(
    zipcode = zipcode,
    road = road,
    landNumber = landNumber,
    detailAddress = detailAddress,
    isBasic = isBasic
)
