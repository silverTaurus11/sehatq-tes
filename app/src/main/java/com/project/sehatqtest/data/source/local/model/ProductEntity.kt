package com.project.sehatqtest.data.source.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_entity")
data class ProductEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") var id: Int?= 0,
    @ColumnInfo(name = "imageUrl") var imageUrl: String?="",
    @ColumnInfo(name = "title") var title: String?= "",
    @ColumnInfo(name = "description") var description: String?= "",
    @ColumnInfo(name = "price") var price: String?= "",
    @ColumnInfo(name = "loved") var loved: Int?= 0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(imageUrl)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(price)
        parcel.writeValue(loved)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductEntity> {
        override fun createFromParcel(parcel: Parcel): ProductEntity {
            return ProductEntity(parcel)
        }

        override fun newArray(size: Int): Array<ProductEntity?> {
            return arrayOfNulls(size)
        }
    }

}