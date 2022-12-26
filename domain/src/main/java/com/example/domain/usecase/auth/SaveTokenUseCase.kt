package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(access: String?, refresh: String?, expiredAt: String?) =
        authRepository.saveToken(access, refresh, expiredAt)
}