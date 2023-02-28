package com.example.data.remote.response.address

import com.example.domain.model.AddressModel
import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("results")
    val results: Results,
) {
    data class Results(
        @SerializedName("juso")
        val juso: List<Juso>,
    ) {
        data class Juso(
            @SerializedName("zipNo")
            val zipNo: String,
            @SerializedName("rn")
            val rn: String?,
            @SerializedName("buldMnnm")
            val buldMnnm: String?,
            @SerializedName("roadAddrPart2")
            val roadAddrPart2: String?,
            @SerializedName("siNm")
            val siNm: String?,
            @SerializedName("sggNm")
            val sggNm: String?,
            @SerializedName("bdNm")
            val bdNm: String?,
        )
    }
}

fun AddressResponse.Results.Juso.toEntity() = AddressModel(
    zipcode = zipNo,
    road = "$rn $buldMnnm $roadAddrPart2",
    landNumber = "$siNm $sggNm",
    detailAddress = bdNm,
    false
)