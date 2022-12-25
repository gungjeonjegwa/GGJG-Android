package com.example.domain.repository

import com.example.domain.entity.bread.BreadEntity
import com.example.domain.entity.bread.DetailBreadEntity


interface BreadRepository {
    suspend fun allBread(page: String, size: String): BreadEntity
    suspend fun categoryBread(page: String, size: String, category: String): BreadEntity
    suspend fun detailBread(id: String): DetailBreadEntity
}