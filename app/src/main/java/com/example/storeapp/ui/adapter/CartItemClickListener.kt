package com.example.storeapp.ui.adapter

import com.example.storeapp.data.entity.ProductEntity

interface CartItemClickListener {
    fun onItemClick(productEntity: ProductEntity)
}