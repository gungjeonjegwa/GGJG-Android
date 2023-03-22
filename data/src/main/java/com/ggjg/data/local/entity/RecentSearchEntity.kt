package com.ggjg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ggjg.domain.entity.bread.RecentSearchEntity as RecentSearchDomainEntity

@Entity(tableName = "recent_search")
data class RecentSearchEntity(
    @PrimaryKey val search: String,
)

fun RecentSearchDomainEntity.toDbEntity() = RecentSearchEntity(
    search = search
)

fun RecentSearchEntity.toEntity() = RecentSearchDomainEntity(
    search = search
)