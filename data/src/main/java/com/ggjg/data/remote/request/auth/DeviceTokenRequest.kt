package com.ggjg.data.remote.request.auth

import com.ggjg.domain.param.auth.DeviceTokenParam
import com.google.gson.annotations.SerializedName

data class DeviceTokenRequest(
    @SerializedName("deviceToken")
    val deviceToken: String,
)

fun DeviceTokenParam.toRequest() = DeviceTokenRequest(
    deviceToken = deviceToken
)