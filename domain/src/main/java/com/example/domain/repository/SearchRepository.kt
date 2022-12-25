package com.example.domain.repository

import com.example.domain.entity.search.RecentSearchEntity

interface SearchRepository {
    suspend fun searchBread(recentSearchEntity: RecentSearchEntity)
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}