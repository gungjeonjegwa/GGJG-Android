package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class GetRecentSearchUseCase @Inject constructor(
    private val breadRepository: BreadRepository
) {
    suspend fun execute() =
        breadRepository.getRecentSearch()
}