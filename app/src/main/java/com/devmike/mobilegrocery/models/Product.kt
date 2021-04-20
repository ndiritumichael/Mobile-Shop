package com.devmike.mobilegrocery.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(

    @PrimaryKey(autoGenerate = true)
    var Id:Int=0,
    val name: String,
    val pricePerUnit: Double,
    val image: Int,
    val description: String

)
