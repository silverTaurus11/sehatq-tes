package com.project.sehatqtest.domain.usecase.home

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.Resource
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.domain.repository.IProductRepository
import javax.inject.Inject

class HomeInteractor @Inject constructor(private val productRepository: IProductRepository): HomeUseCase {
    override fun getProducts(): LiveData<Resource<PagedList<ProductEntity>>> {
        return productRepository.getProducts()
    }

    override fun getFavoriteProducts(): LiveData<PagedList<ProductEntity>> {
        return productRepository.getFavoriteProduct()
    }

    override fun getCategories(): LiveData<PagedList<CategoryEntity>> {
        return productRepository.getCategories()
    }

    override fun updateFavoriteProductItem(item: ProductEntity) {
        productRepository.updateFavoriteProductItem(item)
    }
}