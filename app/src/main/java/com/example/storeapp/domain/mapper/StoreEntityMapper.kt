package com.example.storeapp.domain.mapper

import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products

class StoreEntityMapper {
    fun storeMapper(products: Products): ProductEntity{
        return ProductEntity(title = products.title, price = products.price, category = products.category,
            description = products.description, image = products.image)
    }
}