package com.ggjg.data.repository

import com.ggjg.data.remote.datasource.CouponDataSource
import com.ggjg.data.remote.response.coupon.toEntity
import com.ggjg.domain.entity.coupon.CouponEntity
import com.ggjg.domain.repository.CouponRepository
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val couponDataSource: CouponDataSource,
) : CouponRepository {
    override suspend fun enrollCoupon(code: String) =
        couponDataSource.enrollCoupon(code)

    override suspend fun allCoupon(): List<CouponEntity> =
        couponDataSource.allCoupon().map { it.toEntity() }

    override suspend fun availableCoupon(id: String): List<CouponEntity> =
        couponDataSource.availableCoupon(id).map { it.toEntity() }
}