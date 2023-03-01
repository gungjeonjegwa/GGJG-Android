package com.example.data.remote.datasource

import com.example.data.remote.api.CouponAPI
import com.example.data.remote.response.coupon.CouponResponse
import com.example.data.utils.GGJGApiCall
import javax.inject.Inject

class CouponDataSourceImpl @Inject constructor(
    private val couponAPI: CouponAPI,
) : CouponDataSource {
    override suspend fun enrollCoupon(code: String) = GGJGApiCall {
        couponAPI.enrollCoupon(code = code)
    }

    override suspend fun allCoupon(): List<CouponResponse> = GGJGApiCall {
        couponAPI.allCoupon()
    }

    override suspend fun availableCoupon(id: String): List<CouponResponse> = GGJGApiCall {
        couponAPI.availableCoupon(id = id)
    }
}