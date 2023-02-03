package com.example.domain.usecase.auth

import com.example.domain.param.auth.SignUpParam
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(signUpParam: SignUpParam) = kotlin.runCatching {
        authRepository.signUp(signUpParam)
    }
}