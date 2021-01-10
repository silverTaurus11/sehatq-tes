package com.project.sehatqtest.domain.usecase.detail

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity

interface ProductDetailUseCase {
    fun getProduct(id: Int): LiveData<ProductEntity>
    fun insertPurchaseHistory(item: PurchaseHistoryEntity)
}