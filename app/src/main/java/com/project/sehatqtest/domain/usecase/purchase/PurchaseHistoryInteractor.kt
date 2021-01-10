package com.project.sehatqtest.domain.usecase.purchase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.domain.repository.IPurchaseHistoryRepository
import javax.inject.Inject

class PurchaseHistoryInteractor @Inject constructor(private val purchaseHistoryEntity: IPurchaseHistoryRepository):
        PurchaseHistoryUseCase {

    override fun getPurchaseHistory(): LiveData<PagedList<PurchaseHistoryEntity>> {
        return purchaseHistoryEntity.getPurchaseHistory()
    }

}