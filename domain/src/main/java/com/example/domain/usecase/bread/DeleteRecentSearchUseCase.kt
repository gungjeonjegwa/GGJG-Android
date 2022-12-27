package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class DeleteRecentSearchUseCase @Inject constructor(
    private val breadRepository: BreadRepository
) {
    suspend fun execute(search: String) =
        breadRepository.deleteSearch(search)
}