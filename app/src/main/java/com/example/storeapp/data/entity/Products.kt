package com.example.storeapp.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Products(
    val id:Int,
    val title:String?,
    val price:Float?,
    val category:String?,
    val description:String?,
    val image:String?,
    var isFav:Boolean?,
    val rating: Rating?
) : Parcelable