package com.project.sehatqtest.domain.usecase.home

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.Resource
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity

interface HomeUseCase {
    fun getProducts(): LiveData<Resource<PagedList<ProductEntity>>>
    fun getFavoriteProducts(): LiveData<PagedList<ProductEntity>>
    fun getCategories(): LiveData<PagedList<CategoryEntity>>
    fun updateFavoriteProductItem(item: ProductEntity)
}