package com.example.data.utils

import com.example.domain.exception.*
import retrofit2.HttpException

class HttpHandler<T> {
    private lateinit var httpRequest: suspend () -> T
    private var onMoreData: (message: String) -> Throwable = { MoreDataException() }
    private var onTokenError: (message: String) -> Throwable = { TokenErrorException() }
    private var onNotFound: (message: String) -> Throwable = { NotFoundException() }
    private var onConflict: (message: String) -> Throwable = { ConflictException() }
    private var onOtherHttpStatusCode: (code: Int, message: String) -> Throwable = { _, _ -> UnknownException() }

    fun httpRequest(httpRequest: suspend () -> T) =
        this.apply { this.httpRequest = httpRequest }

    fun onMoreData(onMoreData: (message: String) -> Throwable) =
        this.apply { this.onMoreData = onMoreData }

    fun onTokenError(onTokenError: (message: String) -> Throwable) =
        this.apply { this.onTokenError = onTokenError }

    fun onNotFound(onNotFound: (message: String) -> Throwable) =
        this.apply { this.onNotFound = onNotFound }

    fun onConflict(onConflict: (message: String) -> Throwable) =
        this.apply { this.onConflict = onConflict }

    fun onOtherHttpStatusCode(onOtherHttpStatusCode: (code: Int, message: String) -> Throwable) =
        this.apply { this.onOtherHttpStatusCode = onOtherHttpStatusCode }

    suspend fun sendRequest(): T =
        try {
            httpRequest()
        } catch (e: HttpException) {
            throw when (e.code()) {
                400 -> onMoreData(e.message())
                401 -> onTokenError(e.message())
                404 -> onNotFound(e.message())
                409 -> onConflict(e.message())
                else -> onOtherHttpStatusCode(e.code(), e.message())
            }
        }
}