package com.ggjg.domain.usecase.auth

import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class RecentAddressUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() = kotlin.runCatching {
        authRepository.recentAddress()
    }
}