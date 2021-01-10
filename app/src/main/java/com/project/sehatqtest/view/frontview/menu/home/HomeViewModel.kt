package com.project.sehatqtest.view.frontview.menu.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.domain.usecase.home.HomeUseCase
import com.project.sehatqtest.helper.RefreshableLiveData

class HomeViewModel @ViewModelInject constructor(private val homeViewUseCase: HomeUseCase): ViewModel() {

    val productsLiveData by lazy { RefreshableLiveData{ homeViewUseCase.getProducts() } }

    fun getCategories() = homeViewUseCase.getCategories()

    fun getFavoriteProducts() = homeViewUseCase.getFavoriteProducts()

    fun updateFavoriteProductItem(item: ProductEntity){
        homeViewUseCase.updateFavoriteProductItem(item)
    }
}