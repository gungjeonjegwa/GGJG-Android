package com.ggjg.domain.usecase.basket

import com.ggjg.domain.repository.BasketRepository
import javax.inject.Inject

class MyBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        basketRepository.myBasket()
    }
}