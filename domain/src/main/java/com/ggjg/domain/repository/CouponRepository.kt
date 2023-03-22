package com.ggjg.domain.repository

import com.ggjg.domain.entity.coupon.CouponEntity

interface CouponRepository {
    suspend fun enrollCoupon(code: String)
    suspend fun allCoupon(): List<CouponEntity>
    suspend fun availableCoupon(id: String): List<CouponEntity>

}