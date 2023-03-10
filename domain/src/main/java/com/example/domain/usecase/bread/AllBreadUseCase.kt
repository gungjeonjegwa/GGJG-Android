package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class AllBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke(page: Int) = kotlin.runCatching {
        breadRepository.allBread(page = page)
    }
}