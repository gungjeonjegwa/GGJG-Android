package com.ggjg.domain.usecase.order

import com.ggjg.domain.repository.OrderRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        orderRepository.createOrder()
    }
}