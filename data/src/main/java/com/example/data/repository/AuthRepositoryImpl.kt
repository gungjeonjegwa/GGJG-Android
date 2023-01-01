package com.example.data.repository

import com.example.data.local.datasorce.LocalAuthDataSource
import com.example.data.remote.datasource.AuthDataSource
import com.example.data.remote.model.toEntity
import com.example.data.remote.model.toRequest
import com.example.data.remote.request.auth.toRequest
import com.example.data.remote.response.auth.toEntity
import com.example.domain.entity.auth.CheckEntity
import com.example.domain.entity.auth.LoginEntity
import com.example.domain.entity.auth.ProfileEntity
import com.example.domain.entity.auth.ProfilePrivateEntity
import com.example.domain.model.AddressModel
import com.example.domain.param.auth.LoginParam
import com.example.domain.param.auth.SignUpParam
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localAuthDataSource: LocalAuthDataSource,
) : AuthRepository {
    override suspend fun signUp(signUpParam: SignUpParam) =
        authDataSource.signUp(signUpParam.toRequest())

    override suspend fun login(loginParam: LoginParam): LoginEntity =
        authDataSource.login(loginParam.toRequest()).toEntity()

    override suspend fun logout() =
        authDataSource.logout()

    override suspend fun idCheck(id: String): CheckEntity =
        authDataSource.idCheck(id).toEntity()

    override suspend fun emailCheck(email: String): CheckEntity =
        authDataSource.emailCheck(email).toEntity()

    override suspend fun changeAddress(address: AddressModel) =
        authDataSource.changeAddress(address.toRequest())

    override suspend fun recentAddress(): List<AddressModel> =
        authDataSource.recentAddress().map { it.toEntity() }

    override suspend fun newAddress(address: AddressModel) =
        authDataSource.newAddress(address.toRequest())

    override suspend fun profile(): ProfileEntity =
        authDataSource.profile().toEntity()

    override suspend fun profilePrivate(): ProfilePrivateEntity =
        authDataSource.profilePrivate().toEntity()

    override suspend fun saveToken(access: String?, refresh: String?, expiredAt: String?) =
        localAuthDataSource.saveToken(access, refresh, expiredAt)

    override suspend fun isLogin(): Boolean =
        !localAuthDataSource.getAccessToken().isNullOrBlank()
}