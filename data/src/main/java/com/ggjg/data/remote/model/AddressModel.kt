package com.ggjg.data.remote.model

import com.google.gson.annotations.SerializedName
import com.ggjg.domain.model.AddressModel as AddressDomainModel

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

fun AddressModel.toEntity() = AddressDomainModel(
    zipcode = zipcode,
    road = road,
    landNumber = landNumber,
    detailAddress = detailAddress,
    isBasic = isBasic
)

fun AddressDomainModel.toRequest() = AddressModel(
    zipcode = zipcode,
    road = road,
    landNumber = landNumber,
    detailAddress = detailAddress,
    isBasic = isBasic
)
