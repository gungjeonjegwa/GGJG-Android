package com.example.domain.entity.order

import com.example.domain.model.AddressModel

data class DetailOrderEntity(
    val address: AddressModel,
    val orderId: String,
    val name: String,
    val phone: String,
    val items: List<BuyItem>
) {
    data class BuyItem(
        val breadId: String,
        val count: Int,
        val price: Int,
        val unit: String?,
        val age: Int?,
    )
}