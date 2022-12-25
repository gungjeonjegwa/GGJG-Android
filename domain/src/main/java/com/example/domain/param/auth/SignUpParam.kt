package com.example.domain.param.auth

data class SignUpParam(
    val id: String,
    val pw: String,
    val name: String,
    val phone: String?,
    val email: String
)