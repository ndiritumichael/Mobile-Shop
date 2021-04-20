package com.devmike.mobilegrocery.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmike.mobilegrocery.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertProducts(product: Product)


@Query("SELECT * FROM Product ")
fun getAllProducts():Flow<List<Product>>

}