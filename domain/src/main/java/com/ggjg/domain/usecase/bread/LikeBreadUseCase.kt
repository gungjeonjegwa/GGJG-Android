package com.ggjg.domain.usecase.bread

import com.ggjg.domain.repository.BreadRepository
import javax.inject.Inject

class LikeBreadUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        breadRepository.likeBread(id)
    }
}