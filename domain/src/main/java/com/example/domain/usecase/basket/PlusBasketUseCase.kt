package com.example.domain.usecase.basket

import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class PlusBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        basketRepository.plusBasket(id)
    }
}