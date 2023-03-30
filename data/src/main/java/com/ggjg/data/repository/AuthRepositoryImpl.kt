package com.ggjg.data.repository

import com.ggjg.data.local.datasorce.LocalAuthDataSource
import com.ggjg.data.remote.datasource.AuthDataSource
import com.ggjg.data.remote.model.toEntity
import com.ggjg.data.remote.model.toRequest
import com.ggjg.data.remote.request.auth.toRequest
import com.ggjg.data.remote.response.auth.toEntity
import com.ggjg.domain.entity.auth.CheckEntity
import com.ggjg.domain.entity.auth.LoginEntity
import com.ggjg.domain.entity.auth.ProfileEntity
import com.ggjg.domain.entity.auth.ProfilePrivateEntity
import com.ggjg.domain.model.AddressModel
import com.ggjg.domain.param.auth.DeviceTokenParam
import com.ggjg.domain.param.auth.LoginParam
import com.ggjg.domain.param.auth.SignUpParam
import com.ggjg.domain.repository.AuthRepository
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

    override suspend fun giftStamp() =
        authDataSource.giftStamp()

    override suspend fun saveToken(access: String?, refresh: String?, expiredAt: String?) =
        localAuthDataSource.saveToken(access, refresh, expiredAt)

    override suspend fun isLogin(): Boolean =
        !localAuthDataSource.getAccessToken().isNullOrBlank()

    override suspend fun saveDeviceToken(deviceTokenParam: DeviceTokenParam) =
        authDataSource.saveDeviceToken(deviceTokenRequest = deviceTokenParam.toRequest())
}