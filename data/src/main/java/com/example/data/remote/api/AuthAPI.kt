package com.example.data.remote.api

import com.example.data.remote.request.auth.LoginRequest
import com.example.data.remote.request.auth.SignUpRequest
import com.example.data.remote.response.auth.IdCheckResponse
import com.example.data.remote.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {
    @POST("/users/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    )

    @POST("users/signin")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("users/idcheck")
    suspend fun idCheck(
        @Query("id") id: String
    ): IdCheckResponse
}