package com.project.sehatqtest.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.project.sehatqtest.data.AppExecutors
import com.project.sehatqtest.data.source.NetworkBoundResourceLiveData
import com.project.sehatqtest.data.source.Resource
import com.project.sehatqtest.data.source.local.LocalDataStore
import com.project.sehatqtest.data.source.local.RoomConfig
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.remote.RemoteDataStore
import com.project.sehatqtest.data.source.remote.model.HomeResponse
import com.project.sehatqtest.data.source.remote.network.ApiResponse
import com.project.sehatqtest.domain.repository.IProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val remoteDataStore: RemoteDataStore,
    private val localDataStore: LocalDataStore,
    private val appExecutors: AppExecutors): IProductRepository {

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(RoomConfig.PAGE_SIZE)
        .setPageSize(RoomConfig.PAGE_SIZE)
        .build()

    override fun getProducts(): LiveData<Resource<PagedList<ProductEntity>>> {
        return object : NetworkBoundResourceLiveData<PagedList<ProductEntity>, HomeResponse>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<ProductEntity>> {
                return LivePagedListBuilder(localDataStore.getProducts(), config).build()
            }

            override fun shouldFetch(data: PagedList<ProductEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<HomeResponse>> {
                return remoteDataStore.getRemoteData()
            }

            override fun saveCallResult(data: HomeResponse) {
                localDataStore.insertCategories(data.data?.category)
                localDataStore.insertOrUpdateProducts(data.data?.productPromoList)
            }

        }.asLiveData()
    }

    override fun getCategories(): LiveData<PagedList<CategoryEntity>> {
        return LivePagedListBuilder(localDataStore.getCategories(), config).build()
    }

    override fun getFavoriteProduct(): LiveData<PagedList<ProductEntity>> {
        return LivePagedListBuilder(localDataStore.getFavoriteProduct(), config).build()
    }

    override fun updateFavoriteProductItem(item: ProductEntity) {
        appExecutors.diskIO().execute {
            localDataStore.updateFavoriteProductItem(item)
        }
    }

    override fun searchProducts(query: String): LiveData<PagedList<ProductEntity>> {
        return LivePagedListBuilder(localDataStore.searchProduct(query), config).build()
    }

    override fun getProduct(id: Int): LiveData<ProductEntity> {
        return localDataStore.getProduct(id)
    }
}