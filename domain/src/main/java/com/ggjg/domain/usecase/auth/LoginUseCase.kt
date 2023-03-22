package com.ggjg.domain.usecase.auth

import com.ggjg.domain.param.auth.LoginParam
import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(loginParam: LoginParam) = kotlin.runCatching {
        authRepository.login(loginParam)
    }
}