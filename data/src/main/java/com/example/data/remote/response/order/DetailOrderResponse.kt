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
        @SerializedName("title")
        val title: String,
        @SerializedName("age")
        val age: Int?,
        @SerializedName("size")
        val size: String?,
        @SerializedName("previewUrl")
        val breadImg: String,
        @SerializedName("unit")
        val unit: String?,
        @SerializedName("extraMoney")
        val extraMoney: Int?,
        @SerializedName("price")
        val price: Int,
        @SerializedName("count")
        val count: Int,
    )

    fun BuyItem.toEntity() = DetailOrderEntity.BuyItem(
        title = title,
        age = age,
        size = size,
        breadImg = breadImg,
        unit = unit,
        extraMoney = extraMoney,
        price = price,
        count = count
    )
}

fun DetailOrderResponse.toEntity() = DetailOrderEntity(
    address = address.toEntity(),
    orderId = orderId,
    name = name,
    phone = phone,
    items = items.map { it.toEntity() }
)