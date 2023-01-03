package com.example.data.remote.response.bread

import com.example.domain.entity.bread.LikeBreadEntity
import com.example.domain.entity.bread.SearchEntity
import com.example.domain.entity.bread.SearchResultEntity
import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val name: String,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("category")
    val category: String,
    @SerializedName("isSoldOut")
    val isSoldOut: Boolean,
    @SerializedName("previewUrl")
    val imgUrl: String,
    @SerializedName("sellDeliveryType")
    val sellDeliveryType: List<SellType>,
    @SerializedName("isLikeItem")
    val isLike: Boolean,
) {
    data class SellType(
        @SerializedName("id")
        val id: String,
        @SerializedName("sellType")
        val type: String,
    )

    fun SellType.toEntity() = SearchResultEntity.SellType(
        id = id,
        type = type
    )
}

fun SearchResultResponse.toEntity() = SearchResultEntity(
    id = id,
    name = name,
    price = price,
    category = category,
    isSoldOut = isSoldOut,
    imgUrl = imgUrl,
    sellDeliveryType = sellDeliveryType.map { it.toEntity() },
    isLike = isLike
)