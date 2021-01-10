package com.project.sehatqtest.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id") val id: Int?=0,
    @SerializedName("imageUrl") val imageUrl: String?="",
    @SerializedName("title") val title: String?= "",
    @SerializedName("description") val description: String?= "",
    @SerializedName("price") val price: String?= "",
    @SerializedName("loved") val loved: Int?= 0
)