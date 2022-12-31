package com.example.data.remote.datasource

import com.example.data.remote.response.coupon.CouponResponse

interface CouponDataSource {
    suspend fun enrollCoupon(code: String)
    suspend fun availableCoupon(id: String): List<CouponResponse>
}