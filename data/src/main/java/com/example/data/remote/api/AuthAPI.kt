package com.example.data.remote.api

import com.example.data.remote.model.AddressModel
import com.example.data.remote.request.auth.LoginRequest
import com.example.data.remote.request.auth.SignUpRequest
import com.example.data.remote.response.auth.CheckResponse
import com.example.data.remote.response.auth.LoginResponse
import com.example.data.remote.response.auth.ProfilePrivateResponse
import com.example.data.remote.response.auth.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {
    @POST("/users/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest,
    )

    @POST("/users/signin")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): LoginResponse

    @POST("/users/signout")
    suspend fun logout()

    @GET("/users/idcheck")
    suspend fun idCheck(
        @Query("id") id: String,
    ): CheckResponse

    @GET("/users/emailcheck")
    suspend fun emailCheck(
        @Query("email") email: String,
    ): CheckResponse

    @POST("/users/address/basic")
    suspend fun changeAddress(
        @Body address: AddressModel,
    )

    @GET("/users/address")
    suspend fun recentAddress(): List<AddressModel>

    @POST("/users/address/lately")
    suspend fun newAddress(
        @Body address: AddressModel,
    )

    @GET("/users/profile/my")
    suspend fun profile(): ProfileResponse

    @GET("/users/profile/private")
    suspend fun profilePrivate(): ProfilePrivateResponse
}