package com.example.domain.usecase.basket

import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class MyBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {
    suspend fun execute() =
        basketRepository.myBasket()
}