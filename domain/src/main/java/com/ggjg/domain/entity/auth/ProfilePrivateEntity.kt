package com.ggjg.domain.entity.auth

import com.ggjg.domain.model.AddressModel

data class ProfilePrivateEntity(
    val name: String,
    val id: String,
    val email: String,
    val phone: String?,
    val address: AddressModel?,
)