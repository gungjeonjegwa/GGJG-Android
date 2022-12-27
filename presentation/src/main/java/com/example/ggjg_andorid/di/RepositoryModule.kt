package com.example.ggjg_andorid.di

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.BasketRepositoryImpl
import com.example.data.repository.BreadRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.BasketRepository
import com.example.domain.repository.BreadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideBreadRepository(
        breadRepositoryImpl: BreadRepositoryImpl,
    ): BreadRepository

    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    abstract fun provideBasketRepository(
        basketRepositoryImpl: BasketRepositoryImpl,
    ): BasketRepository
}