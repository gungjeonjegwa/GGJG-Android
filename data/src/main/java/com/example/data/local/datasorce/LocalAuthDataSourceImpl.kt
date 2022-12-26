package com.example.data.local.datasorce

import com.example.data.local.storage.AuthStorage
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    private val localStorage: AuthStorage
) : LocalAuthDataSource {
    override suspend fun getAccessToken(): String? =
        localStorage.getAccessToken()

    override suspend fun getRefreshToken(): String? =
        localStorage.getRefreshToken()

    override suspend fun getExpiredAt(): String? =
        localStorage.getExpiredAt()

    override suspend fun saveToken(access: String?, refresh: String?, expiredAt: String?) =
        localStorage.run {
            setAccessToken(access)
            setRefreshToken(refresh)
            setExpiredAt(expiredAt)
        }
}