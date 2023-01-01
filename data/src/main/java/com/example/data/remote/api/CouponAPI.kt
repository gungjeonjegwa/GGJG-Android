package com.example.data.remote.api

import com.example.data.remote.response.coupon.CouponResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CouponAPI {
    @POST("/coupon/{couponId}")
    suspend fun enrollCoupon(
        @Path("couponId") code: String,
    )

    @GET("/coupon/my")
    suspend fun allCoupon(): List<CouponResponse>

    @GET("/coupon/my")
    suspend fun availableCoupon(
    @Query("breadId") id: String,
    ): List<CouponResponse>
}