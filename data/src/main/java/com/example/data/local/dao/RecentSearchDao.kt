package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.local.entity.RecentSearchEntity

@Dao
interface RecentSearchDao {
    @Insert(onConflict = REPLACE)
    suspend fun searchBread(recentSearchEntity: RecentSearchEntity)

    @Query("DELETE FROM recent_search WHERE search = :search")
    suspend fun deleteSearch(search: String)

    @Query("SELECT * FROM recent_search")
    suspend fun getRecentSearch(): List<RecentSearchEntity?>
}