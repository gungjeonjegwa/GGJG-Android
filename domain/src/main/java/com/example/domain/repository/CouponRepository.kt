package com.example.domain.repository

interface CouponRepository {
    suspend fun enrollCoupon(code: String)
}