package com.example.data.local.storage

import android.content.SharedPreferences
import javax.inject.Inject

class AuthStorageImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthStorage {
    companion object {
        const val TOKEN = "TOKEN"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
        const val EXPIRED_AT = "EXPIRED_AT"
    }

    override fun setAccessToken(token: String?) =
        setData(ACCESS_TOKEN, token)

    override fun getAccessToken(): String? =
        sharedPreferences.getString(ACCESS_TOKEN, "")

    override fun setRefreshToken(token: String?) =
        setData(REFRESH_TOKEN, token)

    override fun getRefreshToken(): String? =
        sharedPreferences.getString(REFRESH_TOKEN, "")

    override fun setExpiredAt(expiredAt: String?) =
        setData(EXPIRED_AT, expiredAt)

    override fun getExpiredAt(): String? =
        sharedPreferences.getString(EXPIRED_AT, "")

    private fun setData(id: String, data: String?) =
        sharedPreferences.edit().let {
            it.putString(id, data)
            it.apply()
        }
}