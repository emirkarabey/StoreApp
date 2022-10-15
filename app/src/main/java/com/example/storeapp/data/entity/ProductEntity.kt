package com.example.storeapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "price") val price: Float?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "isFav") var isFav: Boolean?
)