package com.project.sehatqtest.data.source.local

import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity
import com.project.sehatqtest.data.source.local.room.Dao
import com.project.sehatqtest.data.source.remote.model.CategoryItem
import com.project.sehatqtest.data.source.remote.model.ProductItem
import javax.inject.Inject
import javax.inject.Singleton
import javax.sql.DataSource

@Singleton
class LocalDataStore @Inject constructor(private val roomDao: Dao) {

    fun getCategories() = roomDao.getCategories()

    fun insertCategories(items: List<CategoryItem>?){
        if(items == null) return
        roomDao.insertCategories(items.map { ConverterHelper.convertToCategoryEntity(it) })
    }

    fun deleteCategories(){
        roomDao.deleteCategories()
    }

    fun getProducts() = roomDao.getProducts()

    fun insertOrUpdateProducts(items: List<ProductItem>?){
        if(items == null) return
        items.forEach {
            if(roomDao.checkProduct(it.id?:0) > 0){
                roomDao.updateProduct(it.id, it.imageUrl, it.title, it.description, it.price)
            } else{
                roomDao.insertProduct(ConverterHelper.convertToProductEntity(it))
            }
        }
    }

    fun deleteProducts(){
        roomDao.deleteProduct()
    }

    fun getFavoriteProduct() = roomDao.getLikedProducts()

    fun updateFavoriteProductItem(item: ProductEntity){
        val loved = item.loved != 1
        roomDao.updateLikedProduct(loved, item.id?:0)
    }

    fun searchProduct(query: String): androidx.paging.DataSource.Factory<Int, ProductEntity>{
        val filter = "%$query%"
        return roomDao.searchProduct(filter)
    }

    fun getPurchaseHistory() = roomDao.getPurchaseHistory()

    fun insertPurchaseHistory(item: PurchaseHistoryEntity){
        roomDao.insertPurchaseHistory(item)
    }

    fun deletePurchaseHistory() {
        roomDao.deletePurchaseHistory()
    }

    fun getProduct(id: Int) = roomDao.getProduct(id)
}