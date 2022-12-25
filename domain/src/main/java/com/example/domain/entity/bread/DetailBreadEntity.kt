package com.example.domain.entity.bread

data class DetailBreadEntity(
    val id: String,
    val content: String,
    val size: String,
    val storage: String,
    val expirationDate: String,
    val imgUrl: String,
    val precaution: String,
    val deliveryNotice: String,
    val allergy: String,
    val ingredient: String,
    val isLike: Boolean,
    val breadSize: List<BreadSize>,
    val breadImage: List<BreadImage>
) {
    data class BreadSize(
        val size: String,
        val extraMoney: Int,
        val unit: String
    )

    data class BreadImage(
        val imgUrl: String,
        val isInfo: Boolean
    )
}