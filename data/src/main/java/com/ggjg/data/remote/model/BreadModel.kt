package com.ggjg.data.remote.model

import com.google.gson.annotations.SerializedName
import com.ggjg.domain.model.BreadModel as BreadDomainModel

data class BreadModel(
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

    fun SellType.toEntity() = BreadDomainModel.SellType(
        id = id,
        type = type
    )
}

fun BreadModel.toEntity() = BreadDomainModel(
    id = id,
    name = name,
    price = price,
    category = category,
    isSoldOut = isSoldOut,
    imgUrl = imgUrl,
    sellDeliveryType = sellDeliveryType.map { it.toEntity() },
    isLike = isLike
)

