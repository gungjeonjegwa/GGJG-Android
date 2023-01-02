package com.example.domain.usecase.bread

import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class BannerUseCase @Inject constructor(
    private val breadRepository: BreadRepository,
) {
    suspend fun execute() =
        breadRepository.banner()
}