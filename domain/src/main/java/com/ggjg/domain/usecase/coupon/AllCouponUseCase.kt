package com.ggjg.domain.usecase.coupon

import com.ggjg.domain.repository.CouponRepository
import javax.inject.Inject

class AllCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        couponRepository.allCoupon()
    }
}