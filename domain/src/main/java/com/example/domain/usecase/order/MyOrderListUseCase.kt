package com.example.domain.usecase.order

import com.example.domain.repository.OrderRepository
import javax.inject.Inject

class MyOrderListUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        orderRepository.myOrderList()
    }
}