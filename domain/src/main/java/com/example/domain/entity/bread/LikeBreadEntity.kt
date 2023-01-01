package com.example.domain.entity.bread

data class LikeBreadEntity(
    val id: String,
    val name: String,
    val price: Int?,
    val category: String,
    val isSoldOut: Boolean,
    val imgUrl: String,
    val sellDeliveryType: List<SellType>,
    val isLike: Boolean,
) {
    data class SellType(
        val id: String,
        val type: String,
    )
}