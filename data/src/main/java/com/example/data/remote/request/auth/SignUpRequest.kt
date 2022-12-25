package com.example.data.remote.request.auth

import com.example.domain.param.auth.SignUpParam
import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val pw: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String
)

fun SignUpParam.toRequest() = SignUpRequest(
    id = id,
    pw = pw,
    name = name,
    phone = phone,
    email = email
)
