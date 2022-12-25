package com.example.data.remote.datasource

import com.example.data.remote.request.auth.SignUpRequest

interface AuthDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest)
}