package com.example.domain.entity.auth

data class LoginEntity(
    val accessToken: String,
    val refreshToken: String
)