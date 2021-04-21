package com.devmike.mobilegrocery.ui.productDetails

import androidx.lifecycle.ViewModel
import com.devmike.mobilegrocery.models.Product
import com.devmike.mobilegrocery.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsDetailsViewModel @Inject constructor( val repository: Repository) : ViewModel() {

    fun addCart(product: Product){
        repository.addOrderitem(product)
    }

}