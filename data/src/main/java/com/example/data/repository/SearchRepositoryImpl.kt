package com.example.data.repository

import com.example.data.local.datasorce.LocalSearchDataSource
import com.example.data.local.entity.toDbEntity
import com.example.data.local.entity.toEntity
import com.example.domain.entity.RecentSearchEntity
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localSearchDataSource: LocalSearchDataSource
): SearchRepository {
    override suspend fun searchBread(recentSearchEntity: RecentSearchEntity) =
        localSearchDataSource.searchBread(recentSearchEntity.toDbEntity())

    override suspend fun deleteSearch(search: String) =
        localSearchDataSource.deleteSearch(search)

    override suspend fun getRecentSearch(): List<RecentSearchEntity?> =
        localSearchDataSource.getRecentSearch().map { it?.toEntity() }
}