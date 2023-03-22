package com.ggjg.domain.usecase.order

import com.ggjg.domain.param.order.BuyBreadParam
import com.ggjg.domain.repository.OrderRepository
import javax.inject.Inject

class BuyBreadUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(buyBreadParam: BuyBreadParam) = kotlin.runCatching {
        orderRepository.buyBread(buyBreadParam)
    }
}