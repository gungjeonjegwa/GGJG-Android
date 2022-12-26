package com.example.domain.repository

import com.example.domain.entity.auth.CheckEntity
import com.example.domain.entity.auth.LoginEntity
import com.example.domain.param.auth.LoginParam
import com.example.domain.param.auth.SignUpParam

interface AuthRepository {
    suspend fun signUp(signUpParam: SignUpParam)
    suspend fun login(loginParam: LoginParam): LoginEntity
    suspend fun idCheck(id: String): CheckEntity
    suspend fun emailCheck(email: String): CheckEntity
    suspend fun saveToken(access: String?, refresh: String?, expiredAt: String?)
}