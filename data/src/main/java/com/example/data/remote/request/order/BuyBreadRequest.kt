package com.example.data.remote.request.order

import com.example.data.remote.model.AddressModel
import com.example.data.remote.model.toRequest
import com.example.domain.param.order.BuyBreadParam
import com.google.gson.annotations.SerializedName

data class BuyBreadRequest(
    @SerializedName("isPayment")
    val isPayment: Boolean,
    @SerializedName("address")
    val address: AddressModel,
    @SerializedName("orderId")
    val orderId: String,
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
        @SerializedName("discountPrice")
        val discountPrice: Int?,
        @SerializedName("unit")
        val unit: String?,
        @SerializedName("age")
        val age: Int?,
        @SerializedName("couponId")
        val couponId: String?,
    )
}

fun BuyBreadParam.toRequest() = BuyBreadRequest(
    isPayment = isPayment,
    address = address.toRequest(),
    orderId = orderId,
    items = items.map { it.toRequest() }
)

fun BuyBreadParam.BuyItem.toRequest() = BuyBreadRequest.BuyItem(
    breadId = breadId,
    count = count,
    discountPrice = discountPrice,
    price = price,
    unit = unit,
    age = age,
    couponId = couponId
)