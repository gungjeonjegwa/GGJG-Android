package com.example.domain.entity.order

import com.example.domain.model.AddressModel

data class InitOrderEntity(
    val address: AddressModel?,
    val name: String,
    val phone: String,
)