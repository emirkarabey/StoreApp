package com.example.storeapp.repository

import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products
import com.example.storeapp.data.local.FavoriteDao
import com.example.storeapp.data.local.CartDao
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val dbDao: CartDao,
    private val dbFavDao: FavoriteDao
){
    fun getAllProducts() = dbDao.getAllProducts()
    suspend fun addProduct(product: ProductEntity) = dbDao.save(product)
    suspend fun deleteProduct(productId: Int) = dbDao.deleteProduct(productId)
    suspend fun addFavorite(product: ProductEntity) = dbFavDao.save(product)
    fun getAllFavorites() = dbFavDao.getAllProducts()
    suspend fun deleteFavorite(productId: Int) = dbFavDao.deleteProduct(productId)
}