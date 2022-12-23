package com.example.data.repository

import com.example.data.remote.datasource.AuthDataSource
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
}