package com.project.sehatqtest.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.sehatqtest.data.source.local.RoomConfig
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity

@Database(entities = [CategoryEntity::class, ProductEntity::class, PurchaseHistoryEntity::class],
        version = RoomConfig.currentVersion, exportSchema = false)
abstract class MyRoomDataBase: RoomDatabase() {
    abstract fun myRoomDao(): Dao
}