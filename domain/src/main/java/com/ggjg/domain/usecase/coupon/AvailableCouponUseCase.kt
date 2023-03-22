package com.ggjg.domain.usecase.coupon

import com.ggjg.domain.repository.CouponRepository
import javax.inject.Inject

class AvailableCouponUseCase @Inject constructor(
    private val couponRepository: CouponRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        couponRepository.availableCoupon(id)
    }
}