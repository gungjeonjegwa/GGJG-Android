package com.example.data.remote.datasource

import com.example.data.remote.api.CouponAPI
import javax.inject.Inject

class CouponDataSourceImpl @Inject constructor(
    private val couponAPI: CouponAPI,
) : CouponDataSource {
}