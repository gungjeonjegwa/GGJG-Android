package com.example.domain.entity.bread

data class BannerEntity(
    val id: String,
    val imgUrl: String,
    val webUrl: String?,
    val position: String,
    val createdAt: String,
    val modifiedAt: String,
)