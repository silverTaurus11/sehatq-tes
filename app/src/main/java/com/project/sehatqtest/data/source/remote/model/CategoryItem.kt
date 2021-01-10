package com.project.sehatqtest.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("imageUrl") val imageUrl: String?= "",
    @SerializedName("id") val id: Int?=0,
    @SerializedName("name") val name: String?=""
)