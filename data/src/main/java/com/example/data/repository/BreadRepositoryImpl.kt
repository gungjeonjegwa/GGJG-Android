package com.example.data.repository

import com.example.data.local.datasorce.LocalBreadDataSource
import com.example.data.local.entity.toEntity
import com.example.data.remote.datasource.BreadDataSource
import com.example.data.remote.model.toEntity
import com.example.data.remote.response.bread.toEntity
import com.example.domain.entity.bread.*
import com.example.domain.model.BreadModel
import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class BreadRepositoryImpl @Inject constructor(
    private val breadDataSource: BreadDataSource,
    private val localBreadDataSource: LocalBreadDataSource,
) : BreadRepository {
    override suspend fun allBread(page: Int): BreadEntity =
        breadDataSource.allBread(page = page).toEntity()

    override suspend fun categoryBread(page: Int, category: String): BreadEntity =
        breadDataSource.categoryBread(page = page, category = category).toEntity()

    override suspend fun detailBread(id: String): DetailBreadEntity =
        breadDataSource.detailBread(id = id).toEntity()

    override suspend fun likeBread(id: String) =
        breadDataSource.likeBread(id = id)

    override suspend fun allLikeBread(): List<BreadModel> =
        breadDataSource.allLikeBread().map { it.toEntity() }

    override suspend fun banner(): List<BannerEntity> =
        breadDataSource.banner().map { it.toEntity() }

    override suspend fun searchBread(title: String): List<SearchEntity> =
        breadDataSource.searchBread(title = title).map { it.toEntity() }

    override suspend fun resultBread(title: String): List<BreadModel> {
        localBreadDataSource.searchBread(com.example.data.local.entity.RecentSearchEntity(title))
        return breadDataSource.resultBread(title = title).map { it.toEntity() }
    }

    override suspend fun deleteSearch(search: String) =
        localBreadDataSource.deleteSearch(search = search)

    override suspend fun getRecentSearch(): List<RecentSearchEntity?> =
        localBreadDataSource.getRecentSearch().map { it?.toEntity() }
}