package com.ggjg.domain.usecase.coupon

import com.ggjg.domain.repository.CouponRepository
import javax.inject.Inject

class EnrollCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
) {
    suspend operator fun invoke(code: String) = kotlin.runCatching {
        couponRepository.enrollCoupon(code)
    }
}