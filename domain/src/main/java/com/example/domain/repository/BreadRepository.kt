package com.example.domain.repository

import com.example.domain.entity.bread.*


interface BreadRepository {
    suspend fun allBread(page: String, size: String): BreadEntity
    suspend fun categoryBread(page: String, size: String, category: String): BreadEntity
    suspend fun detailBread(id: String): DetailBreadEntity
    suspend fun likeBread(id: String)
    suspend fun allLikeBread(): List<LikeBreadEntity>
    suspend fun banner(): List<BannerEntity>
    suspend fun searchBread(recentSearchEntity: RecentSearchEntity)
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}