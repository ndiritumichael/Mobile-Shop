package com.devmike.mobilegrocery.repository

import android.util.Log
import com.devmike.mobilegrocery.data.ProductsDao
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.models.Product
import javax.inject.Inject

class Repository @Inject constructor(val productsDao: ProductsDao) {
val orderItemsList = mutableListOf<OrdersItem>()
   val allProducts =productsDao.getAllProducts()

    suspend fun insertProduct(product: Product) = productsDao.insertProducts(product)


fun addOrderitem(product: Product){
var ordersItem  = OrdersItem()
ordersItem.product =product
    if(orderItemsList.contains(ordersItem.product)){
        Log.d("repository","increased count Item Added to Cart")
ordersItem = orderItemsList[orderItemsList.indexOf(ordersItem)]
        orderItemsList[orderItemsList.indexOf(ordersItem)].quantity = orderItemsList[orderItemsList.indexOf(
            ordersItem
        )].quantity?.plus(
            1
        )

return


    }
    Log.d("repository","New Item Added to Cart")
    ordersItem.quantity =1
ordersItem.product = product
orderItemsList.add(ordersItem)



}

    fun itemCount(): Int {
        var total = 0
        for (i in orderItemsList) {
            val orderItem: OrdersItem = i
            total += orderItem.quantity!!
        }
        return total
    }


    fun totalCost(): Double {
        var total:Double = 0.0
        for (i in orderItemsList) {
            val orderItem: OrdersItem = i
            total += orderItem.quantity!!.toDouble() * orderItem.product!!.pricePerUnit
        }
        return total
    }
}