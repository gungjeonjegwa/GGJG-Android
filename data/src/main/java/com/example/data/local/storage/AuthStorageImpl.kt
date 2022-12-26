package com.example.data.local.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
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
        getSharedPreferences().getString(ACCESS_TOKEN, "")

    override fun setRefreshToken(token: String?) =
        setData(REFRESH_TOKEN, token)

    override fun getRefreshToken(): String? =
        getSharedPreferences().getString(REFRESH_TOKEN, "")

    override fun setExpiredAt(expiredAt: String?) =
        setData(EXPIRED_AT, expiredAt)

    override fun getExpiredAt(): String? =
        getSharedPreferences().getString(EXPIRED_AT, "")

    private fun setData(id: String, data: String?) =
        getSharedPreferences().edit().let {
            it.putString(id, data)
            it.apply()
        }

    private fun getSharedPreferences() =
        context.getSharedPreferences(TOKEN, MODE_PRIVATE)
}