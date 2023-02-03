package com.example.domain.usecase.auth

import com.example.domain.param.auth.LoginParam
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(loginParam: LoginParam) = kotlin.runCatching {
        authRepository.login(loginParam)
    }
}