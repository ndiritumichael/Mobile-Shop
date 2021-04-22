package com.devmike.mobilegrocery.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertProducts(product: Product)


@Query("SELECT * FROM Product ")
fun getAllProducts():Flow<List<Product>>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertOrder(orderItem:OrdersItem)

@Update
suspend fun updateOrder(orderItem: OrdersItem)

@Query("SELECT * FROM OrdersItem")
fun getAllOrders():Flow<List<OrdersItem>>

@Query("SELECT * FROM OrdersItem where productId = :id " )
suspend fun getSingleOrder(id:Int):List<OrdersItem>


@Query("DELETE  FROM OrdersItem ")
suspend fun clearCart()

@Query("DELETE FROM OrdersItem WHERE orderItemId = :id")
suspend fun deleteSingleOrder(id: Int)

}