package com.project.sehatqtest.view.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.domain.usecase.detail.ProductDetailUseCase
import com.project.sehatqtest.domain.usecase.home.HomeUseCase

class ProductDetailViewModel @ViewModelInject constructor(private val productDetailUseCase: ProductDetailUseCase,
                                                          private val homeUseCase: HomeUseCase): ViewModel() {

    fun getProduct(id: Int) = productDetailUseCase.getProduct(id)

    fun insertToPurchaseHistory(item: ProductEntity){
        productDetailUseCase.insertPurchaseHistory(PurchaseHistoryEntity().apply {
            product = item
            qty = 1
            dateInMillis = System.currentTimeMillis()
        })
    }

    fun updateFavoriteProduct(item: ProductEntity){
        homeUseCase.updateFavoriteProductItem(item)
    }
}