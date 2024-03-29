package com.ggjg.domain.usecase.auth

import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class EmailCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String) = kotlin.runCatching {
        authRepository.emailCheck(email)
    }
}