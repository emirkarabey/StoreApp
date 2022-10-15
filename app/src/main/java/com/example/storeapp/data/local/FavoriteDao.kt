package com.example.storeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.storeapp.data.entity.ProductEntity

@Dao
interface FavoriteDao {
    @Insert
    suspend fun save(product: ProductEntity)
    @Update
    suspend fun update(product: ProductEntity)

    @Query("DELETE FROM store WHERE uid=:productId")
    suspend fun deleteProduct(productId: Int)

    @Query("SELECT * FROM store")
    fun getAllProducts(): List<ProductEntity>
}