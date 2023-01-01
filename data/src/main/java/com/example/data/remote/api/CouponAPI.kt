package com.example.data.remote.api

import com.example.data.remote.response.coupon.CouponResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CouponAPI {
    @POST("/coupon/{couponId}")
    suspend fun enrollCoupon(
        @Path("couponId") code: String,
    )

    @GET("/coupon/my")
    suspend fun allCoupon(): List<CouponResponse>

    @GET("/coupon/my/{breadId}")
    suspend fun availableCoupon(
        @Path("breadId") id: String,
    ): List<CouponResponse>
}