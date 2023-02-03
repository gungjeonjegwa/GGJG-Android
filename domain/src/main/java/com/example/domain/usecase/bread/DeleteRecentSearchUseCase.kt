package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class DeleteRecentSearchUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke(search: String) = kotlin.runCatching {
        breadRepository.deleteSearch(search)
    }
}