package com.example.data.remote.datasource

import com.example.data.remote.request.auth.LoginRequest
import com.example.data.remote.request.auth.SignUpRequest
import com.example.data.remote.response.auth.IdCheckResponse
import com.example.data.remote.response.auth.LoginResponse

interface AuthDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest)
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun idCheck(id: String): IdCheckResponse
}