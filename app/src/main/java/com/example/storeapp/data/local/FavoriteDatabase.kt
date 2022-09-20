package com.example.storeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storeapp.data.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}