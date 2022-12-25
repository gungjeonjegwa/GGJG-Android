package com.example.data.remote.response.bread

import com.example.domain.entity.bread.DetailBreadEntity
import com.google.gson.annotations.SerializedName

data class DetailBreadResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("stroage")
    val storage: String,
    @SerializedName("expirationDate")
    val expirationDate: String,
    @SerializedName("previewUrl")
    val imgUrl: String,
    @SerializedName("precaution")
    val precaution: String,
    @SerializedName("deliveryNotice")
    val deliveryNotice: String,
    @SerializedName("allergy")
    val allergy: String,
    @SerializedName("ingredient")
    val ingredient: String,
    @SerializedName("isLikeItem")
    val isLike: Boolean,
    @SerializedName("breadSize")
    val breadSize: List<BreadSize>,
    @SerializedName("breadImage")
    val breadImage: List<BreadImage>
) {
    data class BreadSize(
        @SerializedName("size")
        val size: String,
        @SerializedName("extramoney")
        val extraMoney: Int,
        @SerializedName("unit")
        val unit: String
    )

    fun BreadSize.toEntity() = DetailBreadEntity.BreadSize(
        size = size,
        extraMoney = extraMoney,
        unit = unit
    )

    data class BreadImage(
        @SerializedName("imageUrl")
        val imgUrl: String,
        @SerializedName("isImageInfo")
        val isInfo: Boolean
    )

    fun BreadImage.toEntity() = DetailBreadEntity.BreadImage(
        imgUrl = imgUrl,
        isInfo = isInfo
    )
}

fun DetailBreadResponse.toEntity() = DetailBreadEntity(
    id = id,
    content = content,
    size = size,
    storage = storage,
    expirationDate = expirationDate,
    imgUrl = imgUrl,
    precaution = precaution,
    deliveryNotice = deliveryNotice,
    allergy = allergy,
    ingredient = ingredient,
    isLike = isLike,
    breadSize = breadSize.map { it.toEntity() },
    breadImage = breadImage.map { it.toEntity() }
)