package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.model.BreadModel
import com.ggjg.data.remote.response.bread.BannerResponse
import com.ggjg.data.remote.response.bread.BreadResponse
import com.ggjg.data.remote.response.bread.DetailBreadResponse
import com.ggjg.data.remote.response.bread.SearchResponse

interface BreadDataSource {
    suspend fun allBread(page: Int): BreadResponse
    suspend fun categoryBread(page: Int, category: String): BreadResponse
    suspend fun detailBread(id: String): DetailBreadResponse
    suspend fun likeBread(id: String)
    suspend fun allLikeBread(): List<BreadModel>
    suspend fun banner(): List<BannerResponse>
    suspend fun searchBread(title: String): List<SearchResponse>
    suspend fun resultBread(title: String): List<BreadModel>
}