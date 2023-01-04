package com.example.data.remote.response.bread

import com.example.data.remote.model.BreadModel
import com.example.data.remote.model.toEntity
import com.example.domain.entity.bread.BreadEntity
import com.google.gson.annotations.SerializedName

data class BreadResponse(
    @SerializedName("list")
    val breadList: List<BreadModel>,
    @SerializedName("last")
    val isLast: Boolean,
)

fun BreadResponse.toEntity() = BreadEntity(
    breadList = breadList.map { it.toEntity() },
    isLast = isLast
)