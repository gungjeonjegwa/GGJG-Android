package com.example.data.repository

import com.example.data.remote.datasource.CouponDataSource
import com.example.domain.repository.CouponRepository
import javax.inject.Inject

class CouponRepositoryImpl @Inject constructor(
    private val couponDataSource: CouponDataSource,
) : CouponRepository {
}