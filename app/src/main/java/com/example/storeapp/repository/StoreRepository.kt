package com.example.storeapp.repository

import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.local.StoreDao
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val dbDao: StoreDao
){
    fun getAllProducts() = dbDao.getAllProducts()
    suspend fun addProduct(product: ProductEntity) = dbDao.save(product)
    suspend fun deleteProduct(productId: Int) = dbDao.deleteProduct(productId)
}