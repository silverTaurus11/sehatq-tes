package com.project.sehatqtest.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.Resource

interface IProductRepository {
    fun getProducts(): LiveData<Resource<PagedList<ProductEntity>>>
    fun getCategories(): LiveData<PagedList<CategoryEntity>>
    fun getFavoriteProduct(): LiveData<PagedList<ProductEntity>>
    fun updateFavoriteProductItem(item: ProductEntity)
    fun searchProducts(query: String): LiveData<PagedList<ProductEntity>>
    fun getProduct(id: Int): LiveData<ProductEntity>
}