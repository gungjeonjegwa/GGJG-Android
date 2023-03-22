package com.ggjg.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ggjg.data.local.dao.RecentSearchDao
import com.ggjg.data.local.entity.RecentSearchEntity

@Database(entities = [RecentSearchEntity::class], version = 1)
abstract class GGJGDataBase: RoomDatabase() {
    abstract fun resentSearchDao(): RecentSearchDao
}