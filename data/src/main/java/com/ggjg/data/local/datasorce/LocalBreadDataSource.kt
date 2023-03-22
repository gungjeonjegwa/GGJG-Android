package com.ggjg.data.local.datasorce

import com.ggjg.data.local.entity.RecentSearchEntity

interface LocalBreadDataSource {
    suspend fun searchBread(recentSearchEntity: RecentSearchEntity)
    suspend fun deleteSearch(search: String)
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}