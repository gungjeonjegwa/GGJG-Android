package com.example.ggjg_andorid.di

import com.example.data.remote.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun provideBreadDataSource(
        breadDataSourceImpl: BreadDataSourceImpl,
    ): BreadDataSource

    @Binds
    abstract fun provideAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl,
    ): AuthDataSource

    @Binds
    abstract fun provideBasketDataSource(
        basketDataSourceImpl: BasketDataSourceImpl,
    ): BasketDataSource

    @Binds
    abstract fun provideOrderDataSource(
        orderDataSourceImpl: OrderDataSourceImpl,
    ): OrderDataSource
}