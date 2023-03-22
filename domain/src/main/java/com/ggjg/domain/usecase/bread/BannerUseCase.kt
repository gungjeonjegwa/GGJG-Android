package com.ggjg.domain.usecase.bread

import com.ggjg.domain.repository.BreadRepository
import javax.inject.Inject

class BannerUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        breadRepository.banner()
    }
}