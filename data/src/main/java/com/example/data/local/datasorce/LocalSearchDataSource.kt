package com.example.data.local.datasorce

import com.example.data.local.entity.RecentSearchEntity

interface LocalSearchDataSource {
    suspend fun searchBread(recentSearchEntity: RecentSearchEntity)
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}