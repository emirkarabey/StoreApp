package com.example.storeapp.data.local

import androidx.room.*
import com.example.storeapp.data.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Insert
    suspend fun save(product: ProductEntity)
    @Update
    suspend fun update(product: ProductEntity)

    @Query("DELETE FROM store WHERE uid=:productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * FROM store")
    fun getAllProducts(): List<ProductEntity>
}