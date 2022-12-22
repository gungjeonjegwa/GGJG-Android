package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class AllBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository
) {
    suspend fun execute(page: String, size: String) =
        breadRepository.allBread(page, size)
}