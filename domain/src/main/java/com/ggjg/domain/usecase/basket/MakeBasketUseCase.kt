package com.ggjg.domain.usecase.basket

import com.ggjg.domain.param.basket.MakeBasketParam
import com.ggjg.domain.repository.BasketRepository
import javax.inject.Inject

class MakeBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
) {
    suspend operator fun invoke(makeBasketParam: List<MakeBasketParam>) = kotlin.runCatching {
        basketRepository.makeBasket(makeBasketParam)
    }
}