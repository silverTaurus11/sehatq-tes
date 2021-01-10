package com.project.sehatqtest.view.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.domain.usecase.home.HomeUseCase
import com.project.sehatqtest.domain.usecase.search.SearchUseCase

class SearchViewModel @ViewModelInject constructor(private val searchUseCase: SearchUseCase,
                                                   private val homeUseCase: HomeUseCase): ViewModel() {

    fun searchProduct(query: String) = searchUseCase.searchUseCase(query)

    fun updateFavoriteProduct(item: ProductEntity) = homeUseCase.updateFavoriteProductItem(item)
}