package com.example.ggjg_andorid.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.RecentSearchDao
import com.example.data.local.database.GGJGDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    fun provideGGJGDataBase(
        @ApplicationContext context: Context
    ): GGJGDataBase = Room
        .databaseBuilder(context, GGJGDataBase::class.java, "GGJGDataBase")
        .build()

    @Provides
    fun provideRecentSearchDao(
        ggjgDataBase: GGJGDataBase
    ): RecentSearchDao = ggjgDataBase.resentSearchDao()
}