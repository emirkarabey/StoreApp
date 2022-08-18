package com.example.storeapp.repository

import com.example.storeapp.data.api.ApiFactory
import com.example.storeapp.data.entity.Products
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiFactory: ApiFactory
) {



    //suspend fun getData(
    //    page : Int
    //): CharacterResponse{
    //    return apiFactory.getAllCharacter(page)
    //}

    suspend fun getProducts(): List<Products> {
        return apiFactory.getAllProducts()
    }

}