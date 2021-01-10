package com.project.sehatqtest.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.sehatqtest.data.source.local.model.CategoryEntity
import com.project.sehatqtest.data.source.local.model.ProductEntity
import com.project.sehatqtest.data.source.local.model.PurchaseHistoryEntity

@Dao
interface Dao {
    @Query("SELECT * FROM category_entity ORDER BY id ASC")
    fun getCategories(): DataSource.Factory<Int, CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(stories: List<CategoryEntity>)

    @Query("Delete FROM category_entity")
    fun deleteCategories()

    @Query("SELECT * FROM product_entity ORDER BY id ASC")
    fun getProducts(): DataSource.Factory<Int, ProductEntity>

    @Query("SELECT * FROM product_entity WHERE id= :id")
    fun getProduct(id: Int): LiveData<ProductEntity>

    @Query("SELECT COUNT(id) FROM product_entity WHERE id =:id")
    fun checkProduct(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(stories: ProductEntity)

    @Query("UPDATE product_entity SET imageUrl = :imageUrl, title = :title, description = :description, price = :price WHERE id = :id")
    fun updateProduct(id: Int?, imageUrl: String?, title: String?, description: String?, price: String?)

    @Query("DELETE FROM product_entity")
    fun deleteProduct()

    @Query("SELECT * FROM product_entity WHERE loved = 1")
    fun getLikedProducts(): DataSource.Factory<Int, ProductEntity>

    @Query("UPDATE product_entity SET loved = :loved WHERE id = :id")
    fun updateLikedProduct(loved: Boolean, id: Int)

    @Query("SELECT * FROM product_entity WHERE title LIKE :query")
    fun searchProduct(query: String): DataSource.Factory<Int, ProductEntity>

    @Query("SELECT * FROM purchase_history_entity ORDER BY transaction_date DESC")
    fun getPurchaseHistory(): DataSource.Factory<Int, PurchaseHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchaseHistory(item: PurchaseHistoryEntity)

    @Query("DELETE FROM purchase_history_entity")
    fun deletePurchaseHistory()

}