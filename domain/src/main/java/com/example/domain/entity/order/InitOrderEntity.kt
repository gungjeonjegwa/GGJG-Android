package com.example.domain.entity.order

data class InitOrderEntity(
    val address: Address?,
    val name: String,
    val phone: String,
) {
    data class Address(
        val zipcode: String,
        val road: String,
        val landNumber: String,
        val detailAddress: String,
        val isBasic: Boolean,
    )
}