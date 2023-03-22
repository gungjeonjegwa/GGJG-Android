package com.ggjg.domain.entity.order

data class MyOrderListEntity(
    val orderId: String,
    val deliveryStatus: String,
    val title: String,
    val price: Int,
    val breadImg: String,
    val createdDate: String
)