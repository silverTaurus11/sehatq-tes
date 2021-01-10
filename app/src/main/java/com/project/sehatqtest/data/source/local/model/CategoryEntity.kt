package com.project.sehatqtest.data.source.local.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_entity")
data class CategoryEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id") var id: Int?= 0,
        @ColumnInfo(name = "name") var name: String?= "",
        @ColumnInfo(name = "imageUrl") var imageUrl: String?= ""
)