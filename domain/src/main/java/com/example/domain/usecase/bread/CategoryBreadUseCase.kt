package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class CategoryBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke(page: Int, category: String) = kotlin.runCatching {
        breadRepository.categoryBread(page = page, category = category)
    }
}