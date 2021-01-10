package com.project.sehatqtest.domain.usecase.detail

import androidx.lifecycle.LiveData
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.domain.repository.IProductRepository
import com.project.sehatqtest.domain.repository.IPurchaseHistoryRepository
import javax.inject.Inject

class ProductDetailInteractor @Inject constructor(private val productRepository: IProductRepository,
                                                  private val purchaseHistoryRepository: IPurchaseHistoryRepository): ProductDetailUseCase {
    override fun getProduct(id: Int): LiveData<ProductEntity> {
        return productRepository.getProduct(id)
    }

    override fun insertPurchaseHistory(item: PurchaseHistoryEntity) {
        purchaseHistoryRepository.insertPurchaseHistory(item)
    }
}