package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.api.AuthAPI
import com.ggjg.data.remote.model.AddressModel
import com.ggjg.data.remote.request.auth.LoginRequest
import com.ggjg.data.remote.request.auth.SignUpRequest
import com.ggjg.data.remote.response.auth.CheckResponse
import com.ggjg.data.remote.response.auth.LoginResponse
import com.ggjg.data.remote.response.auth.ProfilePrivateResponse
import com.ggjg.data.remote.response.auth.ProfileResponse
import com.ggjg.data.utils.GGJGApiCall
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI,
) : AuthDataSource {
    override suspend fun signUp(signUpRequest: SignUpRequest) = GGJGApiCall {
        authAPI.signUp(signUpRequest = signUpRequest)
    }

    override suspend fun login(loginRequest: LoginRequest): LoginResponse = GGJGApiCall {
        authAPI.login(loginRequest = loginRequest)
    }

    override suspend fun logout() = GGJGApiCall {
        authAPI.logout()
    }

    override suspend fun idCheck(id: String): CheckResponse = GGJGApiCall {
        authAPI.idCheck(id = id)
    }

    override suspend fun emailCheck(email: String): CheckResponse = GGJGApiCall {
        authAPI.emailCheck(email = email)
    }

    override suspend fun changeAddress(address: AddressModel) = GGJGApiCall {
        authAPI.changeAddress(address = address)
    }

    override suspend fun recentAddress(): List<AddressModel> = GGJGApiCall {
        authAPI.recentAddress()
    }

    override suspend fun newAddress(address: AddressModel) = GGJGApiCall {
        authAPI.newAddress(address = address)
    }

    override suspend fun profile(): ProfileResponse = GGJGApiCall {
        authAPI.profile()
    }

    override suspend fun profilePrivate(): ProfilePrivateResponse = GGJGApiCall {
        authAPI.profilePrivate()
    }

    override suspend fun giftStamp() = GGJGApiCall {
        authAPI.giftStamp()
    }
}