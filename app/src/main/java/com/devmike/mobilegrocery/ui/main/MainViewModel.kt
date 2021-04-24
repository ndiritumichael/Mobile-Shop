package com.devmike.mobilegrocery.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.models.Product
import com.devmike.mobilegrocery.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository): ViewModel() {

  val allProducts
  get() = repository.allProducts.asLiveData()

  val allOrdersItem
  get() = repository.allOrders.asLiveData()

  fun deleteOrder(id:Int) = viewModelScope.launch {
    repository.deleteOrderItem(id)
  }

    fun clearcart()  = viewModelScope.launch {
      repository.clearCart()
    }


}