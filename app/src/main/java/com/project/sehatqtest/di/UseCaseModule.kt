package com.project.sehatqtest.di

import com.project.sehatqtest.domain.usecase.detail.ProductDetailInteractor
import com.project.sehatqtest.domain.usecase.detail.ProductDetailUseCase
import com.project.sehatqtest.domain.usecase.frontview.FrontViewInteractor
import com.project.sehatqtest.domain.usecase.frontview.FrontViewUseCase
import com.project.sehatqtest.domain.usecase.home.HomeInteractor
import com.project.sehatqtest.domain.usecase.home.HomeUseCase
import com.project.sehatqtest.domain.usecase.login.LoginInteractor
import com.project.sehatqtest.domain.usecase.login.LoginUseCase
import com.project.sehatqtest.domain.usecase.logout.LogoutInteractor
import com.project.sehatqtest.domain.usecase.logout.LogoutUseCase
import com.project.sehatqtest.domain.usecase.purchase.PurchaseHistoryInteractor
import com.project.sehatqtest.domain.usecase.purchase.PurchaseHistoryUseCase
import com.project.sehatqtest.domain.usecase.search.SearchInteractor
import com.project.sehatqtest.domain.usecase.search.SearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun provideLoginUseCase(loginInteractor: LoginInteractor): LoginUseCase

    @Binds
    abstract fun provideHomeUseCase(homeInteractor: HomeInteractor): HomeUseCase

    @Binds
    abstract fun provideSearchUseCase(searchInteractor: SearchInteractor): SearchUseCase

    @Binds
    abstract fun providePurchaseHistoryUseCase(purchaseHistoryInteractor: PurchaseHistoryInteractor): PurchaseHistoryUseCase

    @Binds
    abstract fun provideProductDetailUseCase(productDetailInteractor: ProductDetailInteractor): ProductDetailUseCase

    @Binds
    abstract fun provideLogoutUseCase(logoutInteractor: LogoutInteractor): LogoutUseCase

    @Binds
    abstract fun provideFrontViewUseCase(frontViewInteractor: FrontViewInteractor): FrontViewUseCase

}