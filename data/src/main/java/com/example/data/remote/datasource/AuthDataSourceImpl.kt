package com.example.data.remote.datasource

import com.example.data.remote.api.AuthAPI
import com.example.data.remote.request.auth.LoginRequest
import com.example.data.remote.request.auth.SignUpRequest
import com.example.data.remote.response.auth.CheckResponse
import com.example.data.remote.response.auth.LoginResponse
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthDataSource {
    override suspend fun signUp(signUpRequest: SignUpRequest) =
        authAPI.signUp(signUpRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        authAPI.login(loginRequest)

    override suspend fun idCheck(id: String): CheckResponse =
        authAPI.idCheck(id)

    override suspend fun emailCheck(email: String): CheckResponse =
        authAPI.emailCheck(email)
}