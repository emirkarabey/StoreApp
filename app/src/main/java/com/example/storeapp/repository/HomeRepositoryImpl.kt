package com.example.storeapp.repository

import com.example.storeapp.data.api.ApiFactory
import com.example.storeapp.data.entity.Categories
import com.example.storeapp.data.entity.Products
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory
) {

    suspend fun getProducts(): List<Products> {
        return apiFactory.getAllProducts()
    }


    suspend fun getCategories(): Categories {
        return apiFactory.getAllCategories()
    }



}