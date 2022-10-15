package com.example.storeapp.ui.adapter

import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products

interface CartItemClickListener {
    fun onItemClick(product: Products)
}