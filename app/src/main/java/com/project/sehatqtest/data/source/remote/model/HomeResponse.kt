package com.project.sehatqtest.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("data") val data: HomeInfo?= null
)

data class HomeInfo(
    @SerializedName("category") val category: List<CategoryItem>?= null,
    @SerializedName("productPromo") val productPromoList: List<ProductItem>?= null
)