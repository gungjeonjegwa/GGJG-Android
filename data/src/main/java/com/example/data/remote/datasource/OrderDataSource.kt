package com.example.data.remote.datasource

import com.example.data.remote.response.order.InitOrderInfoResponse

interface OrderDataSource {
    suspend fun initOrderInfo(): InitOrderInfoResponse
}