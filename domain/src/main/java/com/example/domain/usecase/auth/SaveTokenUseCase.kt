package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(access: String? = null, refresh: String? = null, expiredAt: String? = null) =
        kotlin.runCatching {
            authRepository.saveToken(access, refresh, expiredAt)
        }
}