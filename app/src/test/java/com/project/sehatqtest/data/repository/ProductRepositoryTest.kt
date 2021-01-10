package com.project.sehatqtest.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import com.project.sehatqtest.data.AppExecutors
import com.project.sehatqtest.data.source.Resource
import com.project.sehatqtest.data.source.local.ConverterHelper
import com.project.sehatqtest.data.source.local.LocalDataStore
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.remote.RemoteDataStore
import com.project.sehatqtest.data.source.remote.model.CategoryItem
import com.project.sehatqtest.data.source.remote.model.ProductItem
import com.project.sehatqtest.util.LiveDataTestUtil
import com.project.sehatqtest.util.LiveDataTestUtil.observeForTesting
import com.project.sehatqtest.util.PagedListUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ProductRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val remoteDataStore = Mockito.mock(RemoteDataStore::class.java)
    private val localDataStore = Mockito.mock(LocalDataStore::class.java)

    private val categoryItem = listOf(
        CategoryItem("lala", 1, "jaja"),
        CategoryItem("lulu", 2, "juju"),
        CategoryItem("lele", 3, "jeje"),
        CategoryItem("lolo", 4, "jojo")
    )

    private val productItem = listOf(
        ProductItem(1, "wewe", "wewe", "wewewe","$100", 0),
        ProductItem(2, "wawa", "wawa", "wawawa","$200", 1),
        ProductItem(3, "wiwi", "wiwi", "wiwiwi","$300", 1),
        ProductItem(4, "wowo", "wowo", "wowowo","$400", 0)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun cleanUp() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun getProduct(){
        val productRepository = Mockito.mock(ProductRepository::class.java)
        doAnswer {
            MutableLiveData<Resource<PagedList<ProductEntity>>>().apply {
                value = Resource.Success(PagedListUtil.mockPagedList(productItem.map {
                    ConverterHelper.convertToProductEntity(it)
                }))
            }
        }.`when`(productRepository).getProducts()
        val liveData = productRepository.getProducts()
        val liveDataValue = LiveDataTestUtil.getValue(liveData)
        Assert.assertTrue(liveDataValue is Resource.Success<PagedList<ProductEntity>>)
    }

    @Test
    fun getCategories(){
        val productRepository = Mockito.mock(ProductRepository::class.java)
        doAnswer {
            MutableLiveData<PagedList<CategoryEntity>>().apply {
                value =  PagedListUtil.mockPagedList(categoryItem.map { ConverterHelper.convertToCategoryEntity(it) })
            }
        }.`when`(productRepository).getCategories()
        val data = productRepository.getCategories()
        data.observeForTesting {
            Assert.assertEquals(data.value!!.size, categoryItem.size)
        }
    }

    @Test
    fun getFavoriteProduct(){
        val productRepository = Mockito.mock(ProductRepository::class.java)
        val favoriteProductItem = productItem.filter { item -> item.loved == 1 }
        doAnswer {
            MutableLiveData<PagedList<ProductEntity>>().apply {
                value =  PagedListUtil.mockPagedList(favoriteProductItem.map { ConverterHelper.convertToProductEntity(it) })
            }
        }.`when`(productRepository).getFavoriteProduct()
        val data = productRepository.getFavoriteProduct()
        data.observeForTesting {
            Assert.assertEquals(data.value!!.size, favoriteProductItem.size)
        }
    }

    @Test
    fun searchProduct(){
        val productRepository = Mockito.mock(ProductRepository::class.java)
        val searchProduct = productItem.filter { item -> item.title?.contains("Wii")?:false}
        doAnswer {
            MutableLiveData<PagedList<ProductEntity>>().apply {
                value =  PagedListUtil.mockPagedList(searchProduct.map { ConverterHelper.convertToProductEntity(it) })
            }
        }.`when`(productRepository).searchProducts(anyString())
        val data = productRepository.searchProducts(anyString())
        data.observeForTesting {
            Assert.assertEquals(data.value!!.size, searchProduct.size)
        }
    }

    @Test
    fun getProductById(){
        val productRepository = ProductRepository(remoteDataStore, localDataStore, appExecutors)
        val productSelected = productItem.find { it.id == 1 }?.let { item -> ConverterHelper.convertToProductEntity(item) }
        doAnswer {
            MutableLiveData<ProductEntity>().apply { value = productSelected}
        }.`when`(localDataStore).getProduct(anyInt())
        val data = productRepository.getProduct(anyInt())
        verify(localDataStore).getProduct(anyInt())
        data.observeForTesting {
            val dataValue = data.value
            Assert.assertNotNull(dataValue)
            Assert.assertEquals(dataValue?.id, productSelected?.id)
            Assert.assertEquals(dataValue?.title, productSelected?.title)
            Assert.assertEquals(dataValue?.description, productSelected?.description)
            Assert.assertEquals(dataValue?.loved, productSelected?.loved)
            Assert.assertEquals(dataValue?.imageUrl, productSelected?.imageUrl)
        }
    }


}