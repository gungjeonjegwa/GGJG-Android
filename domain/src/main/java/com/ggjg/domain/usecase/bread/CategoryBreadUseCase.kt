package com.ggjg.domain.usecase.bread

import com.ggjg.domain.repository.BreadRepository
import javax.inject.Inject

class CategoryBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke(page: Int, category: String) = kotlin.runCatching {
        breadRepository.categoryBread(page = page, category = category)
    }
}