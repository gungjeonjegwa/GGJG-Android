package com.example.domain.usecase.coupon

import com.example.domain.repository.CouponRepository
import javax.inject.Inject

class AllCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        couponRepository.allCoupon()
    }
}