package com.example.ggjg_andorid.di

import com.example.data.local.datasorce.LocalAuthDataSource
import com.example.data.local.datasorce.LocalAuthDataSourceImpl
import com.example.data.local.datasorce.LocalSearchDataSource
import com.example.data.local.datasorce.LocalSearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    abstract fun provideLocalSearchDataSource(
        localSearchDataSourceImpl: LocalSearchDataSourceImpl
    ): LocalSearchDataSource

    @Binds
    abstract fun provideLocalAuthDataSource(
        localAuthDataSourceImpl: LocalAuthDataSourceImpl
    ): LocalAuthDataSource
}