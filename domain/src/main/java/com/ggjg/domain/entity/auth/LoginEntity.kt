package com.ggjg.domain.entity.auth

data class LoginEntity(
    val accessToken: String,
    val refreshToken: String,
    val expiredAt: String
)