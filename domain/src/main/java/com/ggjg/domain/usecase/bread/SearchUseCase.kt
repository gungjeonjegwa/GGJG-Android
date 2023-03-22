package com.ggjg.domain.usecase.bread

import com.ggjg.domain.repository.BreadRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke(title: String) = kotlin.runCatching {
        breadRepository.searchBread(title)
    }
}