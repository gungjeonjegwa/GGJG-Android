package com.example.data.remote.response.auth

import com.example.domain.entity.auth.LoginEntity
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("expiredAt")
    val expiredAt: String
)

fun LoginResponse.toEntity() = LoginEntity(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiredAt = expiredAt
)