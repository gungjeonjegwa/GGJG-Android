package com.example.domain.usecase.basket

import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class PlusBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend fun execute(id: String) =
        basketRepository.plusBasket(id)
}