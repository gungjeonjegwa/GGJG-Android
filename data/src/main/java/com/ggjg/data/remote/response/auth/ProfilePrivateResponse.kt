package com.ggjg.data.remote.response.auth

import com.ggjg.data.remote.model.AddressModel
import com.ggjg.data.remote.model.toEntity
import com.ggjg.domain.entity.auth.ProfilePrivateEntity
import com.google.gson.annotations.SerializedName

data class ProfilePrivateResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("address")
    val address: AddressModel?,
)

fun ProfilePrivateResponse.toEntity() = ProfilePrivateEntity(
    name = name,
    id = id,
    email = email,
    phone = phone,
    address = address?.toEntity()
)