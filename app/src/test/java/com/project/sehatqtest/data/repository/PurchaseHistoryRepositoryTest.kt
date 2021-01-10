package com.project.sehatqtest.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doAnswer
import com.project.sehatqtest.data.source.local.LocalDataStore
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.util.LiveDataTestUtil.observeForTesting
import com.project.sehatqtest.util.PagedListUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class PurchaseHistoryRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val localDataStore = Mockito.mock(LocalDataStore::class.java)
    private val purchaseHistoryRepository = Mockito.mock(PurchaseHistoryRepository::class.java)
    private val purchaseHistoryList by lazy { listOf(
        PurchaseHistoryEntity(1, ProductEntity(1), 1, 100211),
        PurchaseHistoryEntity(2, ProductEntity(2), 1, 100222),
        PurchaseHistoryEntity(3, ProductEntity(3), 1, 100223),
        PurchaseHistoryEntity(4, ProductEntity(1), 1, 100224)
    ) }

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getPurchaseHistory(){
        doAnswer {
            Mockito.mock(DataSource.Factory::class.java)
                    as DataSource.Factory<Int, PurchaseHistoryEntity>
        }.`when`(localDataStore).getPurchaseHistory()

        doAnswer {
            MutableLiveData<PagedList<PurchaseHistoryEntity>>().apply {
                value = PagedListUtil.mockPagedList(purchaseHistoryList)
            }
        }.`when`(purchaseHistoryRepository).getPurchaseHistory()

        val purchaseHistoryListLiveData = purchaseHistoryRepository.getPurchaseHistory()
        purchaseHistoryListLiveData.observeForTesting {
            Assert.assertEquals(purchaseHistoryListLiveData.value!!.size, purchaseHistoryList.size)
        }
    }
}