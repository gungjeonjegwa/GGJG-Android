package com.example.data.remote.datasource

import com.example.data.remote.response.bread.*

interface BreadDataSource {
    suspend fun allBread(page: String, size: String): BreadResponse
    suspend fun categoryBread(page: String, size: String, category: String): BreadResponse
    suspend fun detailBread(id: String): DetailBreadResponse
    suspend fun likeBread(id: String)
    suspend fun allLikeBread(): List<LikeBreadResponse>
    suspend fun banner(): List<BannerResponse>
    suspend fun searchBread(title: String): List<SearchResponse>
}