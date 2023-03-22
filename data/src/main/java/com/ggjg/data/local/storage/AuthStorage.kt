package com.ggjg.data.local.storage

interface AuthStorage {
    fun setAccessToken(token: String?)
    fun getAccessToken(): String?

    fun setRefreshToken(token: String?)
    fun getRefreshToken(): String?

    fun setExpiredAt(expiredAt: String?)
    fun getExpiredAt(): String?
}