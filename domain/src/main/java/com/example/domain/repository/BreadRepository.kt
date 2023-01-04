package com.example.domain.repository

import com.example.domain.entity.bread.*
import com.example.domain.model.BreadModel


interface BreadRepository {
    suspend fun allBread(page: String, size: String): BreadEntity
    suspend fun categoryBread(page: String, size: String, category: String): BreadEntity
    suspend fun detailBread(id: String): DetailBreadEntity
    suspend fun likeBread(id: String)
    suspend fun allLikeBread(): List<BreadModel>
    suspend fun banner(): List<BannerEntity>
    suspend fun searchBread(title: String): List<SearchEntity>
    suspend fun resultBread(title: String): List<BreadModel>
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}