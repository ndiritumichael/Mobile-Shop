package com.devmike.mobilegrocery.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Parcelize
data class Product(

    @PrimaryKey(autoGenerate = true)
    var Id:Int=0,
    val name: String,
    val pricePerUnit: Double,
    val image: String,
    val description: String

):Parcelable
