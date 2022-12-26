package com.example.data.remote.response.bread

import com.example.domain.entity.bread.DetailBreadEntity
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class DetailBreadResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val name: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("deliveryPrice")
    val deliveryPrice: Int,
    @SerializedName("isSoldOut")
    val isSoldOut: Boolean,
    @SerializedName("size")
    val size: String?,
    @SerializedName("storage")
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
    val breadImage: List<String>,
    @SerializedName("breadImageInfo")
    val breadImageInfo: List<String>,
    @SerializedName("sellDeliveryType")
    val deliveryType: List<DeliveryType>
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
        extraMoney = if (extraMoney == 0) null else "${DecimalFormat("#,###").format(extraMoney)}원",
        unit = unit
    )

    data class DeliveryType(
        @SerializedName("id")
        val id: String,
        @SerializedName("sellType")
        val sellType: String
    )

    fun DeliveryType.toEntity() = DetailBreadEntity.DeliveryType(
        id = id,
        sellType = sellType
    )
}

fun DetailBreadResponse.toEntity() = DetailBreadEntity(
    id = id,
    name = name,
    content = content,
    price = if (price == null) "매장판매" else DecimalFormat("#,###").format(price),
    deliveryPrice = "${DecimalFormat("#,###").format(deliveryPrice)}원",
    isSoldOut = isSoldOut,
    size = size,
    storage = storage,
    expirationDate = expirationDate,
    imgUrl = imgUrl,
    precaution = precaution.replace(".", ".\n"),
    deliveryNotice = deliveryNotice.replace(".", ".\n"),
    allergy = allergy,
    ingredient = ingredient,
    isLike = isLike,
    breadSize = breadSize.map { it.toEntity() },
    breadImage = breadImage,
    breadImageInfo = breadImageInfo,
    deliveryType = deliveryType.map { it.toEntity() }
)