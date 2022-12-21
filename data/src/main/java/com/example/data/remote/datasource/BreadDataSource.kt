package com.example.data.remote.datasource

import com.example.data.remote.response.BreadResponse

interface BreadDataSource {
    suspend fun allBread(page: String, size: String): BreadResponse
}