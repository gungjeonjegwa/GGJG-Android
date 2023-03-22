package com.ggjg.domain.usecase.basket

import com.ggjg.domain.repository.BasketRepository
import javax.inject.Inject

class MinusBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        basketRepository.minusBasket(id)
    }
}