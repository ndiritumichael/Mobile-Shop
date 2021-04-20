package com.devmike.mobilegrocery.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Orders(
    @PrimaryKey(autoGenerate = true)
    val orderId : Int
){
}