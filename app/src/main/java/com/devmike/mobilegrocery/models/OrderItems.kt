package com.devmike.mobilegrocery.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class OrdersItem(
    @PrimaryKey(autoGenerate = true)
    val orderItemId : Int? = null,
    var quantity: Int = 99,
    var unitPrice : Double? = null,
    var productId : Int,
    @Embedded
    var product : Product?

):Parcelable