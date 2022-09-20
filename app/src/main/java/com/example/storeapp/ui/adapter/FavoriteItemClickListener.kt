package com.example.storeapp.ui.adapter

import com.example.storeapp.data.entity.ProductEntity

interface FavoriteItemClickListener {
    fun onItemClick(productEntity: ProductEntity)
}