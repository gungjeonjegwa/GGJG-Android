package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.model.AddressModel
import com.ggjg.data.remote.request.auth.LoginRequest
import com.ggjg.data.remote.request.auth.SignUpRequest
import com.ggjg.data.remote.response.auth.CheckResponse
import com.ggjg.data.remote.response.auth.LoginResponse
import com.ggjg.data.remote.response.auth.ProfilePrivateResponse
import com.ggjg.data.remote.response.auth.ProfileResponse

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
    suspend fun profilePrivate(): ProfilePrivateResponse
    suspend fun giftStamp()
}