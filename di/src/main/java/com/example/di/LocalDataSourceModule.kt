package com.example.di

import com.example.data.local.datasorce.LocalAuthDataSource
import com.example.data.local.datasorce.LocalAuthDataSourceImpl
import com.example.data.local.datasorce.LocalBreadDataSource
import com.example.data.local.datasorce.LocalBreadDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Singleton
    @Binds
    abstract fun provideLocalBreadDataSource(
        localBreadDataSourceImpl: LocalBreadDataSourceImpl
    ): LocalBreadDataSource

    @Singleton
    @Binds
    abstract fun provideLocalAuthDataSource(
        localAuthDataSourceImpl: LocalAuthDataSourceImpl
    ): LocalAuthDataSource
}