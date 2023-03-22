package com.ggjg.data.remote.response.bread

import com.ggjg.data.remote.model.BreadModel
import com.ggjg.data.remote.model.toEntity
import com.ggjg.domain.entity.bread.BreadEntity
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