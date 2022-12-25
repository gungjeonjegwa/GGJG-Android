package com.example.data.remote.datasource

import com.example.data.remote.api.AuthAPI
import com.example.data.remote.request.auth.SignUpRequest
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthDataSource {
    override suspend fun signUp(signUpRequest: SignUpRequest) =
        authAPI.signUp(signUpRequest)
}