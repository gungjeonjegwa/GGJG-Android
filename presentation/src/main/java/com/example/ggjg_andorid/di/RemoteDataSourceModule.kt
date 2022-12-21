package com.example.ggjg_andorid.di

import com.example.data.remote.datasource.BreadDataSource
import com.example.data.remote.datasource.BreadDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun provideBreadDataSource(
        breadDataSourceImpl: BreadDataSourceImpl
    ): BreadDataSource
}