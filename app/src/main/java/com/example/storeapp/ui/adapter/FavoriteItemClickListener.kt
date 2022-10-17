package com.example.storeapp.ui.adapter

import com.example.storeapp.data.entity.Products

interface FavoriteItemClickListener {
    fun onItemClick(products: Products)
}