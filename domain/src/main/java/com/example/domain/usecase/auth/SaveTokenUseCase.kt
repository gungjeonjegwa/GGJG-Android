package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(access: String?, refresh: String?, expiredAt: String?) =
        kotlin.runCatching {
            authRepository.saveToken(access, refresh, expiredAt)
        }
}