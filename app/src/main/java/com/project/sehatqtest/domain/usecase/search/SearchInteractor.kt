package com.project.sehatqtest.domain.usecase.search

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.domain.repository.IProductRepository
import javax.inject.Inject

class SearchInteractor @Inject constructor(private val productRepository: IProductRepository): SearchUseCase {
    override fun searchUseCase(query: String): LiveData<PagedList<ProductEntity>> {
        return productRepository.searchProducts(query)
    }
}