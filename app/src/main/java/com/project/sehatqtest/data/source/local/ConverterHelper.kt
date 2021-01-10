package com.project.sehatqtest.data.source.local

import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.remote.model.CategoryItem
import com.project.sehatqtest.data.source.remote.model.ProductItem

object ConverterHelper {

    fun convertToCategoryEntity(item: CategoryItem) =
        CategoryEntity().apply {
            id = item.id
            name = item.name
            imageUrl = item.imageUrl
        }

    fun convertToProductEntity(item: ProductItem) =
        ProductEntity().apply {
            id = item.id
            imageUrl = item.imageUrl
            title = item.title
            description = item.description
            price = item.price
            loved = item.loved
        }
}