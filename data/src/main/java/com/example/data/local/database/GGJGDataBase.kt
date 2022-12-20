package com.example.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.dao.RecentSearchDao
import com.example.data.local.entity.RecentSearchEntity

@Database(entities = [RecentSearchEntity::class], version = 1)
abstract class GGJGDataBase: RoomDatabase() {
    abstract fun resentSearchDao(): RecentSearchDao
}