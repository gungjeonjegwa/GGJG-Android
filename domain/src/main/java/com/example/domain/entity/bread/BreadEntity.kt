package com.example.domain.entity.bread

data class BreadEntity(
    val breadList: List<Bread>,
    val isLast: Boolean
) {
    data class Bread(
        val id: String,
        val name: String,
        val price: Int?,
        val category: String,
        val isSoldOut: Boolean,
        val imgUrl: String,
        val sellDeliveryType: List<SellType>
    ) {
        data class SellType(
            val id: String,
            val type: String
        )
    }
}