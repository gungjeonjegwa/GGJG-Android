package com.example.data.remote.datasource

import com.example.data.remote.api.AuthAPI
import com.example.data.remote.model.AddressModel
import com.example.data.remote.request.auth.LoginRequest
import com.example.data.remote.request.auth.SignUpRequest
import com.example.data.remote.response.auth.CheckResponse
import com.example.data.remote.response.auth.LoginResponse
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI,
) : AuthDataSource {
    override suspend fun signUp(signUpRequest: SignUpRequest) =
        HttpHandler<Unit>()
            .httpRequest { authAPI.signUp(signUpRequest) }
            .sendRequest()

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        HttpHandler<LoginResponse>()
            .httpRequest { authAPI.login(loginRequest) }
            .sendRequest()

    override suspend fun logout() =
        HttpHandler<Unit>()
            .httpRequest { authAPI.logout() }
            .sendRequest()

    override suspend fun idCheck(id: String): CheckResponse =
        HttpHandler<CheckResponse>()
            .httpRequest { authAPI.idCheck(id) }
            .sendRequest()

    override suspend fun emailCheck(email: String): CheckResponse =
        HttpHandler<CheckResponse>()
            .httpRequest { authAPI.emailCheck(email) }
            .sendRequest()

    override suspend fun changeAddress(address: AddressModel) =
        HttpHandler<Unit>()
            .httpRequest { authAPI.changeAddress(address) }
            .sendRequest()

    override suspend fun recentAddress(): List<AddressModel> =
        HttpHandler<List<AddressModel>>()
            .httpRequest { authAPI.recentAddress() }
            .sendRequest()
}