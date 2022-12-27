package com.example.domain.usecase.basket

import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class MinusBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend fun execute(id: String) =
        basketRepository.minusBasket(id)
}