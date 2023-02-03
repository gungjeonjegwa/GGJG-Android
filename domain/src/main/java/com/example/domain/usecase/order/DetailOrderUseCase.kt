package com.example.domain.usecase.order

import com.example.domain.repository.OrderRepository
import javax.inject.Inject

class DetailOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        orderRepository.detailOrder(id)
    }
}