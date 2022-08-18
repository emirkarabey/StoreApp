package com.example.storeapp.di

import android.content.Context
import androidx.room.Room
import com.example.storeapp.data.local.AppDatabase
import com.example.storeapp.data.local.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "store"
@[Module InstallIn(SingletonComponent::class)]
object StoreRoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(
        db:AppDatabase
    ):StoreDao = db.storeDao()
}