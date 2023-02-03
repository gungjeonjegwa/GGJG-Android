package com.example.domain.usecase.order

import com.example.domain.param.order.BuyBreadParam
import com.example.domain.repository.OrderRepository
import javax.inject.Inject

class BuyBreadUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(buyBreadParam: BuyBreadParam) = kotlin.runCatching {
        orderRepository.buyBread(buyBreadParam)
    }
}