package com.example.domain.entity.auth

import com.example.domain.model.AddressModel

data class ProfilePrivateEntity(
    val name: String,
    val id: String,
    val email: String,
    val phone: String?,
    val address: AddressModel?,
)