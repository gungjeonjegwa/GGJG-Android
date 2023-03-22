package com.ggjg.domain.usecase.auth

import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class IdCheckUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(id: String) = kotlin.runCatching {
        authRepository.idCheck(id)
    }
}