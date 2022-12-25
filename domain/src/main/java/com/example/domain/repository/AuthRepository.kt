package com.example.domain.repository

import com.example.domain.param.auth.SignUpParam

interface AuthRepository {
    suspend fun signUp(signUpParam: SignUpParam)
}