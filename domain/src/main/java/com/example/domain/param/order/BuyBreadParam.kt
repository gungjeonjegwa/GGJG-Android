package com.example.domain.param.order

import com.example.domain.model.AddressModel

data class BuyBreadParam(
    val isPayment: Boolean,
    val address: AddressModel,
    val orderId: String,
    val items: List<BuyItem>
) {
    data class BuyItem(
        val breadId: String,
        val count: String,
        val price: String,
        val unit: String,
        val age: String,
    )
}