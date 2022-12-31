package com.example.ggjg_andorid.di

import com.example.data.repository.*
import com.example.domain.repository.*
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

    @Binds
    abstract fun provideOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl,
    ): OrderRepository

    @Binds
    abstract fun provideCouponRepository(
        couponRepositoryImpl: CouponRepositoryImpl,
    ): CouponRepository
}