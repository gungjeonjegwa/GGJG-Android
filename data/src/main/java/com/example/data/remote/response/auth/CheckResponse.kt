package com.example.data.remote.response.auth

import com.example.domain.entity.auth.CheckEntity
import com.google.gson.annotations.SerializedName

data class CheckResponse(
    @SerializedName("isDuplicated")
    val isDuplicated: Boolean
)

fun CheckResponse.toEntity() = CheckEntity(
    isDuplicated = isDuplicated
)