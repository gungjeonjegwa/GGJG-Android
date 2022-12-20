package com.example.data.local.datasorce

import com.example.data.local.dao.RecentSearchDao
import com.example.data.local.entity.RecentSearchEntity
import javax.inject.Inject

class LocalSearchDataSourceImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao
): LocalSearchDataSource {
    override suspend fun searchBread(recentSearchEntity: RecentSearchEntity) =
        recentSearchDao.searchBread(recentSearchEntity)

    override suspend fun deleteSearch(search: String) =
        recentSearchDao.deleteSearch(search)

    override suspend fun getRecentSearch(): List<RecentSearchEntity?> =
        recentSearchDao.getRecentSearch()
}