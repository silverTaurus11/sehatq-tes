package com.project.sehatqtest.data.source.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_history_entity")
data class PurchaseHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "purchaseId") var id: Int?= null,
    @Embedded var product: ProductEntity?= null,
    @ColumnInfo(name = "qty") var qty: Int?= 0,
    @ColumnInfo(name = "transaction_date") var dateInMillis: Long?= 0
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readParcelable(ProductEntity::class.java.classLoader),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Long::class.java.classLoader) as? Long) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeParcelable(product, flags)
        parcel.writeValue(qty)
        parcel.writeValue(dateInMillis)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PurchaseHistoryEntity> {
        override fun createFromParcel(parcel: Parcel): PurchaseHistoryEntity {
            return PurchaseHistoryEntity(parcel)
        }

        override fun newArray(size: Int): Array<PurchaseHistoryEntity?> {
            return arrayOfNulls(size)
        }
    }
}
