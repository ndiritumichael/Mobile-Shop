package com.devmike.mobilegrocery.repository

import com.devmike.mobilegrocery.data.ProductsDao
import com.devmike.mobilegrocery.models.Product
import javax.inject.Inject

class Repository @Inject constructor(val productsDao: ProductsDao) {

   val allProducts =productsDao.getAllProducts()

    suspend fun insertProduct(product: Product) = productsDao.insertProducts(product)
}