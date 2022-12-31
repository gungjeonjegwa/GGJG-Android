package com.example.domain.entity.order

import com.example.domain.model.AddressModel

data class InitOrderEntity(
    var address: AddressModel?,
    val name: String,
    val phone: String,
)