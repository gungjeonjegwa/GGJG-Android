package com.example.data.interceptor

import com.example.data.BuildConfig
import com.example.data.local.storage.AuthStorage
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val authDataStorage: AuthStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url().encodedPath()
        val ignorePath = listOf(
            "/users/signup",
            "/users/signin",
            "/users/refresh",
            "/users/signout",
            "/users/idcheck",
            "/users/emailcheck"
        )
        if (ignorePath.contains(path)) return chain.proceed(request)
        val refreshToken = authDataStorage.getRefreshToken()
        if (!refreshToken.isNullOrBlank()) {
            val expiredAt = LocalDateTime.parse(
                authDataStorage.getExpiredAt(),
            )
            val currentTime = LocalDateTime.now(ZoneId.systemDefault())
            if (expiredAt.isAfter(currentTime)) {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("${BuildConfig.BASE_URL}users/refresh")
                    .post(RequestBody.create(MediaType.parse("application/json"), ""))
                    .addHeader("refreshToken", authDataStorage.getRefreshToken())
                    .build()
                val jsonParser = JsonParser()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val token = jsonParser.parse(response.body()!!.string()) as JsonObject
                    authDataStorage.setAccessToken(token["accessToken"].toString())
                    authDataStorage.setRefreshToken(token["refreshToken"].toString())
                    authDataStorage.setExpiredAt(token["expiredAt"].toString())
                } else {
                    authDataStorage.setAccessToken(null)
                    authDataStorage.setRefreshToken(null)
                    authDataStorage.setExpiredAt(null)
                }
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