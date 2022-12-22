package com.example.domain.repository

import com.example.domain.entity.BreadEntity


interface BreadRepository {
    suspend fun allBread(page: String, size: String): BreadEntity
    suspend fun categoryBread(page: String, size: String, category: String): BreadEntity
}