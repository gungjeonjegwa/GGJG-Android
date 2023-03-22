package com.ggjg.data.local.datasorce

import com.ggjg.data.local.dao.RecentSearchDao
import com.ggjg.data.local.entity.RecentSearchEntity
import javax.inject.Inject

class LocalBreadDataSourceImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
) : LocalBreadDataSource {
    override suspend fun searchBread(recentSearchEntity: RecentSearchEntity) =
        recentSearchDao.searchBread(recentSearchEntity)

    override suspend fun deleteSearch(search: String) =
        recentSearchDao.deleteSearch(search)

    override suspend fun getRecentSearch(): List<RecentSearchEntity?> =
        recentSearchDao.getRecentSearch().reversed()
}