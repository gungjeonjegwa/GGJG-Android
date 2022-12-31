package com.example.data.remote.datasource

import com.example.data.remote.api.CouponAPI
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class CouponDataSourceImpl @Inject constructor(
    private val couponAPI: CouponAPI,
) : CouponDataSource {
    override suspend fun enrollCoupon(code: String) =
        HttpHandler<Unit>()
            .httpRequest { couponAPI.enrollCoupon(code) }
            .sendRequest()
}