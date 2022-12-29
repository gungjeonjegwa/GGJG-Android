package com.example.data.remote.datasource

import com.example.data.remote.response.bread.BreadResponse
import com.example.data.remote.response.bread.DetailBreadResponse

interface BreadDataSource {
    suspend fun allBread(page: String, size: String): BreadResponse
    suspend fun categoryBread(page: String, size: String, category: String): BreadResponse
    suspend fun detailBread(id: String): DetailBreadResponse
    suspend fun likeBread(id: String)
}