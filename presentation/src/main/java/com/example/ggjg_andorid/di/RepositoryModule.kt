package com.example.ggjg_andorid.di

import com.example.data.repository.BreadRepositoryImpl
import com.example.data.repository.SearchRepositoryImpl
import com.example.domain.repository.BreadRepository
import com.example.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun provideBreadRepository(
        breadRepositoryImpl: BreadRepositoryImpl
    ): BreadRepository
}