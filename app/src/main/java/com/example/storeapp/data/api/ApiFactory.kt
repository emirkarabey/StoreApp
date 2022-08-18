package com.example.storeapp.data.api

import com.example.storeapp.data.entity.Products
import retrofit2.Response
import retrofit2.http.GET


interface ApiFactory {

    //https://rickandmortyapi.com/api/  -> BASE URL
    // character/?page=2

    @GET("products")
    suspend fun getAllProducts(
    ): List<Products>

}