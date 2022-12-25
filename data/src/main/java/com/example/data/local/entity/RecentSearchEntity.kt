package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class RecentSearchEntity(
    @PrimaryKey val search: String
)

fun com.example.domain.entity.search.RecentSearchEntity.toDbEntity() = RecentSearchEntity(
    search = search
)

fun RecentSearchEntity.toEntity() = com.example.domain.entity.search.RecentSearchEntity(
    search = search
)