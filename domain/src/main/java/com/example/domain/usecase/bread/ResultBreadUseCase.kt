package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class ResultBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend fun execute(title: String) =
        breadRepository.resultBread(title)
}