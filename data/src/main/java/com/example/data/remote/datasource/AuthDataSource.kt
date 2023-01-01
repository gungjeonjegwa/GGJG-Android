package com.example.data.remote.datasource

import com.example.data.remote.model.AddressModel
import com.example.data.remote.request.auth.LoginRequest
import com.example.data.remote.request.auth.SignUpRequest
import com.example.data.remote.response.auth.CheckResponse
import com.example.data.remote.response.auth.LoginResponse
import com.example.data.remote.response.auth.ProfileResponse

interface AuthDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest)
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun logout()
    suspend fun idCheck(id: String): CheckResponse
    suspend fun emailCheck(email: String): CheckResponse
    suspend fun changeAddress(address: AddressModel)
    suspend fun recentAddress(): List<AddressModel>
    suspend fun newAddress(address: AddressModel)
    suspend fun profile(): ProfileResponse
}