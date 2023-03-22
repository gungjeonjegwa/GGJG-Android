package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.response.coupon.CouponResponse

interface CouponDataSource {
    suspend fun enrollCoupon(code: String)
    suspend fun allCoupon(): List<CouponResponse>
    suspend fun availableCoupon(id: String): List<CouponResponse>
}