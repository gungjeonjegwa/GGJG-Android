package com.ggjg.domain.usecase.order

import com.ggjg.domain.repository.OrderRepository
import javax.inject.Inject

class DetailOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        orderRepository.detailOrder(id)
    }
}