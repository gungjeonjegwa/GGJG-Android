package com.example.domain.repository

import com.example.domain.entity.bread.BreadEntity
import com.example.domain.entity.bread.DetailBreadEntity
import com.example.domain.entity.bread.RecentSearchEntity


interface BreadRepository {
    suspend fun allBread(page: String, size: String): BreadEntity
    suspend fun categoryBread(page: String, size: String, category: String): BreadEntity
    suspend fun detailBread(id: String): DetailBreadEntity
    suspend fun likeBread(id: String)
    suspend fun searchBread(recentSearchEntity: RecentSearchEntity)
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}