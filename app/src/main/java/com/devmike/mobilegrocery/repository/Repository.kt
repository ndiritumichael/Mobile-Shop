package com.devmike.mobilegrocery.repository

import android.util.Log
import com.devmike.mobilegrocery.data.ProductsDao
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.models.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(val productsDao: ProductsDao) {
val orderItemsList = mutableListOf<OrdersItem>()
   val allProducts =productsDao.getAllProducts()

    suspend fun insertProduct(product: Product) = productsDao.insertProducts(product)
    val allOrders = productsDao.getAllOrders()
    suspend fun insertOrder(ordersItem: OrdersItem) = productsDao.insertOrder(ordersItem)

    suspend fun updateOrder(ordersItem: OrdersItem) = productsDao.updateOrder(ordersItem)

    suspend fun getOrder(id:Int) = productsDao.getSingleOrder(id)

    suspend fun clearCart() = productsDao.clearCart()


fun addOrderitem(product: Product){

    GlobalScope.launch {
       val  ordersItemList = getOrder(product.Id)

        if (ordersItemList.isEmpty()){
            val ordersItem = OrdersItem(quantity = 1,
                unitPrice = product.pricePerUnit,
                productId = product.Id,product = product)

            //duplicating price just in case product price changes after order is made wont affect current orders
            insertOrder(ordersItem)



        } else{

            ordersItemList[0].quantity  = ordersItemList[0].quantity + 1
            updateOrder(ordersItemList[0])

        }


    }

}

   suspend fun deleteOrderItem(id: Int) {
       Log.d("adapter","Repository Stuff Clicked")
       productsDao.deleteSingleOrder(id)

    }
}