package com.project.sehatqtest.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.project.sehatqtest.data.AppExecutors
import com.project.sehatqtest.data.source.local.LocalDataStore
import com.project.sehatqtest.data.source.local.RoomConfig
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.domain.repository.IPurchaseHistoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PurchaseHistoryRepository @Inject constructor(
        private val localDataStore: LocalDataStore,
        private val appExecutors: AppExecutors): IPurchaseHistoryRepository{

    private val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(RoomConfig.PAGE_SIZE)
            .setPageSize(RoomConfig.PAGE_SIZE)
            .build()

    override fun getPurchaseHistory(): LiveData<PagedList<PurchaseHistoryEntity>> {
        return LivePagedListBuilder(localDataStore.getPurchaseHistory(), config).build()
    }

    override fun insertPurchaseHistory(item: PurchaseHistoryEntity) {
        appExecutors.diskIO().execute {
            localDataStore.insertPurchaseHistory(item)
        }
    }
}