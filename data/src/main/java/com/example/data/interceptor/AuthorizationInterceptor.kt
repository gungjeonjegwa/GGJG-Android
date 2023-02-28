package com.example.data.interceptor

import com.example.data.BuildConfig
import com.example.data.local.storage.AuthStorage
import com.example.data.utils.removeDot
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDateTime
import java.time.ZoneId
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
                val jsonParser = JsonParser()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val token = jsonParser.parse(response.body!!.string()) as JsonObject
                    authDataStorage.setAccessToken(token["accessToken"].toString().removeDot())
                    authDataStorage.setRefreshToken(token["refreshToken"].toString().removeDot())
                    authDataStorage.setExpiredAt(token["expiredAt"].toString().removeDot())
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