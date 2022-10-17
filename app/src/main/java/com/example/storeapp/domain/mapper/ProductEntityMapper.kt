package com.example.storeapp.domain.mapper

import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products

class ProductEntityMapper : EntityMapper<ProductEntity, Products> {
    override fun mapFromEntity(entity: ProductEntity): Products {
        return  Products(
            id = entity.uid, title = entity.title,
            price = entity.price, category = entity.category,
            description = entity.description, image = entity.image, isFav = entity.isFav
        )
    }

    override fun mapToEntity(domainModel: Products): ProductEntity {
        return ProductEntity(
             title = domainModel.title,
            price = domainModel.price, category = domainModel.category,
            description = domainModel.description, image = domainModel.image, isFav = domainModel.isFav
        )
    }

    fun fromEntityList(initial: List<ProductEntity>): List<Products>{
        return initial.map { mapFromEntity(it) }
    }
    fun toEntityList(initial: List<Products>): List<ProductEntity>{
        return initial.map { mapToEntity(it) }
    }
}