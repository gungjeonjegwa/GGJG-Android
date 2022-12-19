package com.example.data.utils

import retrofit2.HttpException

class HttpHandler<T> {
    private lateinit var httpRequest: suspend () -> T
    private var errorStatus: (code: Int, message: String) -> Throwable = {_, _ -> RuntimeException()}

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    fun onErrorStatus(errorStatus: (code: Int, message: String) -> Throwable) =
        this.apply { this.errorStatus = errorStatus }

    suspend fun sendRequest(): T =
        try {
            httpRequest()
        } catch (e: HttpException) {
            throw errorStatus(e.code(), e.message())
        }
}