package com.example.domain.usecase.basket

import com.example.domain.param.basket.MakeBasketParam
import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class MakeBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
) {
    suspend operator fun invoke(makeBasketParam: List<MakeBasketParam>) = kotlin.runCatching {
        basketRepository.makeBasket(makeBasketParam)
    }
}