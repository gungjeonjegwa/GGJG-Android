package com.example.data.repository

import com.example.data.remote.datasource.BasketDataSource
import com.example.domain.repository.BasketRepository
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketDataSource: BasketDataSource,
) : BasketRepository {
}