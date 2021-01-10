package com.project.sehatqtest.domain.usecase.purchase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity

interface PurchaseHistoryUseCase {
    fun getPurchaseHistory(): LiveData<PagedList<PurchaseHistoryEntity>>
}