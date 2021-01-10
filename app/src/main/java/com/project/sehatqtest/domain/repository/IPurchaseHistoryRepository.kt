package com.project.sehatqtest.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity

interface IPurchaseHistoryRepository {
    fun getPurchaseHistory(): LiveData<PagedList<PurchaseHistoryEntity>>
    fun insertPurchaseHistory(item: PurchaseHistoryEntity)
}