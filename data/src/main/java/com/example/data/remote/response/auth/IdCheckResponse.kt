package com.example.data.remote.response.auth

import com.example.domain.entity.auth.IdCheckEntity
import com.google.gson.annotations.SerializedName

data class IdCheckResponse(
    @SerializedName("isDuplicated")
    val isDuplicated: Boolean
)

fun IdCheckResponse.toEntity() = IdCheckEntity(
    isDuplicated = isDuplicated
)