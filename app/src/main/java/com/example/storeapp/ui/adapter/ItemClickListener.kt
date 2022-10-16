package com.example.storeapp.ui.adapter

import com.example.storeapp.data.entity.Products

interface ItemClickListener {

    fun onItemClick(product : Products)
    fun favOnItemClick(product: Products)
    fun onFragmentItemClick(product: Products)
}