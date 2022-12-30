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
        val title: String,
        val age: Int?,
        val size: String?,
        val breadImg: String,
        val unit: String?,
        val extraMoney: Int?,
        val price: Int,
        val count: Int,
    )
}