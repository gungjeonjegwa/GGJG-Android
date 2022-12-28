package com.example.data.remote.datasource

import com.example.data.remote.api.OrderAPI
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderAPI: OrderAPI,
) : OrderDataSource {
}