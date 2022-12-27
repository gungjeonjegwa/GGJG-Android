package com.example.ggjg_andorid.di

import com.example.data.local.datasorce.LocalAuthDataSource
import com.example.data.local.datasorce.LocalAuthDataSourceImpl
import com.example.data.local.datasorce.LocalBreadDataSource
import com.example.data.local.datasorce.LocalBreadDataSourceImpl
import com.example.data.remote.datasource.BreadDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    abstract fun provideLocalBreadDataSource(
        localBreadDataSourceImpl: LocalBreadDataSourceImpl
    ): LocalBreadDataSource

    @Binds
    abstract fun provideLocalAuthDataSource(
        localAuthDataSourceImpl: LocalAuthDataSourceImpl
    ): LocalAuthDataSource
}