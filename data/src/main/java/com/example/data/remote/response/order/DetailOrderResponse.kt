package com.example.data.remote.response.order

import com.example.data.remote.model.AddressModel
import com.example.data.remote.model.toEntity
import com.example.domain.entity.order.DetailOrderEntity
import com.google.gson.annotations.SerializedName

data class DetailOrderResponse(
    @SerializedName("address")
    val address: AddressModel,
    @SerializedName("orderId")
    val orderId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("list")
    val items: List<BuyItem>,
) {
    data class BuyItem(
        @SerializedName("breadId")
        val breadId: String,
        @SerializedName("count")
        val count: Int,
        @SerializedName("price")
        val price: Int,
        @SerializedName("unit")
        val unit: String?,
        @SerializedName("age")
        val age: Int?,
    )

    fun BuyItem.toEntity() = DetailOrderEntity.BuyItem(
        breadId = breadId,
        count = count,
        price = price,
        unit = unit,
        age = age
    )
}

fun DetailOrderResponse.toEntity() = DetailOrderEntity(
    address = address.toEntity(),
    orderId = orderId,
    name = name,
    phone = phone,
    items = items.map { it.toEntity() }
)