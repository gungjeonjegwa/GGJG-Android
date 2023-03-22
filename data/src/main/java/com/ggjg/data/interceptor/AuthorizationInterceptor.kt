package com.ggjg.data.interceptor

import com.ggjg.data.BuildConfig
import com.ggjg.data.local.storage.AuthStorage
import com.ggjg.data.remote.response.auth.LoginResponse
import com.ggjg.domain.exception.NeedLoginException
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.*
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authDataStorage: AuthStorage,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val ignorePath = listOf(
            "/users/signup",
            "/users/signin",
            "/users/refresh",
            "/users/idcheck",
            "/users/emailcheck"
        )
        if (ignorePath.contains(path)) return chain.proceed(request)
        val refreshToken = authDataStorage.getRefreshToken()
        if (!refreshToken.isNullOrBlank()) {
            val expiredAt = LocalDateTime.parse(
                authDataStorage.getExpiredAt(),
                DateTimeFormatter.ISO_ZONED_DATE_TIME
            )
            val currentTime = LocalDateTime.now(ZoneId.systemDefault())
            if (currentTime.isAfter(expiredAt)) {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("${BuildConfig.BASE_URL}users/refresh")
                    .post("".toRequestBody("application/json".toMediaTypeOrNull()))
                    .addHeader("refreshToken", "${authDataStorage.getRefreshToken()}")
                    .build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val token = Gson().fromJson(
                        response.body!!.string(),
                        LoginResponse::class.java
                    )
                    authDataStorage.setAccessToken(token = token.accessToken)
                    authDataStorage.setRefreshToken(token = token.refreshToken)
                    authDataStorage.setExpiredAt(expiredAt = token.expiredAt)
                } else throw NeedLoginException()
            }
            return chain.proceed(
                request.newBuilder().addHeader(
                    "Authorization",
                    "Bearer ${authDataStorage.getAccessToken()}"
                ).build()
            )
        }
        return chain.proceed(request)
    }
}