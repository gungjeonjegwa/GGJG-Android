package com.example.data.repository

import com.example.data.remote.datasource.BreadDataSource
import com.example.data.remote.response.toEntity
import com.example.domain.entity.BreadEntity
import com.example.domain.repository.BreadRepository
import javax.inject.Inject

class BreadRepositoryImpl @Inject constructor(
    private val breadDataSource: BreadDataSource
): BreadRepository {
    override suspend fun allBread(page: String, size: String): BreadEntity =
        breadDataSource.allBread(page, size).toEntity()

    override suspend fun categoryBread(page: String, size: String, category: String): BreadEntity =
        breadDataSource.categoryBread(page, size, category).toEntity()
}