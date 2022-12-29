package com.example.domain.usecase.order

import com.example.domain.repository.OrderRepository
import javax.inject.Inject

class InitOrderInfoUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend fun execute() =
        orderRepository.initOrderInfo()
}