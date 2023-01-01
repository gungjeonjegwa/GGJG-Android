package com.example.data.remote.response.bread

import com.example.domain.entity.bread.BannerEntity
import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("previewUrl")
    val imgUrl: String,
    @SerializedName("webUrl")
    val webUrl: String?,
    @SerializedName("position")
    val position: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("modifiedAt")
    val modifiedAt: String,
)

fun BannerResponse.toEntity() = BannerEntity(
    id = id,
    imgUrl = imgUrl,
    webUrl = webUrl,
    position = position,
    createdAt = createdAt,
    modifiedAt = modifiedAt
)