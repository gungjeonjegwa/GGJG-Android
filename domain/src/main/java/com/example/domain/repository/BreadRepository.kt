package com.example.domain.repository

import com.example.domain.entity.bread.*


interface BreadRepository {
    suspend fun allBread(page: String, size: String): BreadEntity
    suspend fun categoryBread(page: String, size: String, category: String): BreadEntity
    suspend fun detailBread(id: String): DetailBreadEntity
    suspend fun likeBread(id: String)
    suspend fun allLikeBread(): List<LikeBreadEntity>
    suspend fun banner(): List<BannerEntity>
    suspend fun searchBread(title: String): List<SearchEntity>
    suspend fun resultBread(title: String): List<SearchResultEntity>
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}