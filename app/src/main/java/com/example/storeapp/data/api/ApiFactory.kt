package com.example.storeapp.data.api

import com.example.storeapp.data.entity.Categories
import com.example.storeapp.data.entity.Products
import retrofit2.http.GET


interface ApiFactory {

    @GET("products")
    suspend fun getAllProducts(
    ): List<Products>

    @GET("products/categories")
    suspend fun getAllCategories(
    ): Categories
}