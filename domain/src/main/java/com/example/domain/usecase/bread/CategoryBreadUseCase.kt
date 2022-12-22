package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class CategoryBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository
) {
    suspend fun execute(page: String, size: String, category: String) =
        breadRepository.categoryBread(page, size, category)
}