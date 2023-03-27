package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.api.CouponAPI
import com.ggjg.data.remote.response.coupon.CouponResponse
import handler.GGJGApiCall
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