package com.example.data.repository

import com.example.data.remote.datasource.AuthDataSource
import com.example.data.remote.request.auth.toRequest
import com.example.data.remote.response.auth.toEntity
import com.example.domain.entity.auth.IdCheckEntity
import com.example.domain.entity.auth.LoginEntity
import com.example.domain.param.auth.LoginParam
import com.example.domain.param.auth.SignUpParam
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signUp(signUpParam: SignUpParam) =
        authDataSource.signUp(signUpParam.toRequest())

    override suspend fun login(loginParam: LoginParam): LoginEntity =
        authDataSource.login(loginParam.toRequest()).toEntity()

    override suspend fun idCheck(id: String): IdCheckEntity =
        authDataSource.idCheck(id).toEntity()
}