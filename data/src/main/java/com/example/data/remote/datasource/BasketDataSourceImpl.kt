package com.example.data.remote.datasource

import com.example.data.remote.api.BasketAPI
import javax.inject.Inject

class BasketDataSourceImpl @Inject constructor(
    private val basketAPI: BasketAPI,
) : BasketDataSource {
}