package com.example.data.remote.datasource

import com.example.data.remote.api.AuthAPI
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthDataSource {
}