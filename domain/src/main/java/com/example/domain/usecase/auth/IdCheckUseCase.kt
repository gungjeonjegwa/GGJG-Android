package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class IdCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        authRepository.idCheck(id)
    }
}