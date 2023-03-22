package com.ggjg.domain.usecase.auth

import com.ggjg.domain.param.auth.SignUpParam
import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(signUpParam: SignUpParam) = kotlin.runCatching {
        authRepository.signUp(signUpParam)
    }
}