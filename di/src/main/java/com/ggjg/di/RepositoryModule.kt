package com.ggjg.di

import com.ggjg.data.repository.*
import com.ggjg.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideBreadRepository(
        breadRepositoryImpl: BreadRepositoryImpl,
    ): BreadRepository

    @Singleton
    @Binds
    abstract fun provideAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun provideBasketRepository(
        basketRepositoryImpl: BasketRepositoryImpl,
    ): BasketRepository

    @Singleton
    @Binds
    abstract fun provideOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl,
    ): OrderRepository

    @Singleton
    @Binds
    abstract fun provideCouponRepository(
        couponRepositoryImpl: CouponRepositoryImpl,
    ): CouponRepository

    @Singleton
    @Binds
    abstract fun provideAddressRepository(
        addressRepositoryImpl: AddressRepositoryImpl,
    ): AddressRepository
}