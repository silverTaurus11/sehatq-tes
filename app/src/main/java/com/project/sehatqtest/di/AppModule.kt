package com.project.sehatqtest.di

import android.content.Context
import com.project.sehatqtest.data.source.local.SharedPrefDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefDataStore(@ApplicationContext context: Context): SharedPrefDataStore =
        SharedPrefDataStore.getInstance(context)
}