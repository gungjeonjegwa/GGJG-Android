package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class EmailCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(email: String) =
        authRepository.emailCheck(email)
}