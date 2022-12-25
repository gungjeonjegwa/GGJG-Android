package com.example.data.remote.api

import com.example.data.remote.request.auth.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("/users/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    )
}