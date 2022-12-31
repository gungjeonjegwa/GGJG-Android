package com.example.data.remote.datasource

interface CouponDataSource {
    suspend fun enrollCoupon(code: String)
}