package com.example.data.remote.response.bread

import com.example.domain.entity.bread.BreadEntity
import com.google.gson.annotations.SerializedName

data class BreadResponse(
    @SerializedName("list")
    val breadList: List<Bread>,
    @SerializedName("last")
    val isLast: Boolean
) {
    data class Bread(
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
        val sellDeliveryType: List<SellType>
    ) {
        data class SellType(
            @SerializedName("id")
            val id: String,
            @SerializedName("sellType")
            val type: String,
        )

        fun SellType.toEntity() = BreadEntity.Bread.SellType(
            id = id,
            type = type
        )
    }

    fun Bread.toEntity() = BreadEntity.Bread(
        id = id,
        name = name,
        price = price,
        category = category,
        isSoldOut = isSoldOut,
        imgUrl = imgUrl,
        sellDeliveryType = sellDeliveryType.map { it.toEntity() }
    )
}

fun BreadResponse.toEntity() = BreadEntity(
    breadList = breadList.map { it.toEntity() },
    isLast = isLast
)