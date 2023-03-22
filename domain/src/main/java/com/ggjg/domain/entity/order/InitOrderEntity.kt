package com.ggjg.domain.entity.order

import com.ggjg.domain.model.AddressModel

data class InitOrderEntity(
    var address: AddressModel?,
    val name: String,
    val phone: String,
)