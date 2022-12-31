package com.example.data.remote.api

import retrofit2.http.POST
import retrofit2.http.Path

interface CouponAPI {
    @POST("/coupon/{couponId}")
    suspend fun enrollCoupon(
        @Path("couponId") code: String,
    )
}