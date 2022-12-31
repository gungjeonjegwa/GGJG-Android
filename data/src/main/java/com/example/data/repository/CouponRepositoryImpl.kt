package com.example.data.repository

import com.example.data.remote.datasource.CouponDataSource
import com.example.data.remote.response.coupon.toEntity
import com.example.domain.entity.coupon.CouponEntity
import com.example.domain.repository.CouponRepository
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val couponDataSource: CouponDataSource,
) : CouponRepository {
    override suspend fun enrollCoupon(code: String) =
        couponDataSource.enrollCoupon(code)

    override suspend fun availableCoupon(id: String): List<CouponEntity> =
        couponDataSource.availableCoupon(id).map { it.toEntity() }
}