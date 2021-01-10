package com.project.sehatqtest.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.project.sehatqtest.data.source.local.RoomConfig
import com.project.sehatqtest.data.source.local.room.Dao
import com.project.sehatqtest.data.source.local.room.MyRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyRoomDataBase = databaseBuilder(
        context,
        MyRoomDataBase::class.java, RoomConfig.DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Provides
    fun provideDao(database: MyRoomDataBase): Dao =
        database.myRoomDao()
}