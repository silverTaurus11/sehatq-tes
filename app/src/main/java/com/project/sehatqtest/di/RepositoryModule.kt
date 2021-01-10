package com.project.sehatqtest.di

import com.project.sehatqtest.data.repository.*
import com.project.sehatqtest.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideLoginRepository(loginRepository: LoginRepository): ILoginRepository

    @Binds
    abstract fun provideProductRepository(productRepository: ProductRepository): IProductRepository

    @Binds
    abstract fun providePurchaseHistoryRepository(purchaseHistoryRepository: PurchaseHistoryRepository): IPurchaseHistoryRepository

    @Binds
    abstract fun provideLogoutRepository(logoutRepository: LogoutRepository): ILogoutRepository

    @Binds
    abstract fun provideProfileRepository(profileRepository: ProfileRepository): IProfileRepository
}