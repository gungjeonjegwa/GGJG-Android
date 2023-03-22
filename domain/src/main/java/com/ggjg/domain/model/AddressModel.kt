package com.ggjg.domain.model

data class AddressModel(
    val zipcode: String,
    val road: String,
    val landNumber: String,
    val detailAddress: String?,
    val isBasic: Boolean,
)
