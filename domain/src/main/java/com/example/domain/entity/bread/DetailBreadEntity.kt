package com.example.domain.entity.bread

data class DetailBreadEntity(
    val id: String,
    val name: String,
    val content: String,
    val price: String,
    val deliveryPrice: String,
    val isSoldOut: Boolean,
    val size: String?,
    val count: Int,
    val storage: String,
    val expirationDate: String,
    val imgUrl: String,
    val precaution: String?,
    val deliveryNotice: String?,
    val allergy: String,
    val ingredient: String,
    val isLike: Boolean,
    val breadSize: List<BreadSize>,
    val breadImage: List<String>,
    val breadImageInfo: List<String>,
    val deliveryType: List<DeliveryType>
) {
    data class BreadSize(
        val size: String,
        val extraMoney: String?,
        val unit: String
    )

    data class DeliveryType(
        val id: String,
        val sellType: String
    )
}