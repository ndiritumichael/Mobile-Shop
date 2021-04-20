package com.devmike.mobilegrocery.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.devmike.mobilegrocery.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository): ViewModel() {

  val allProducts
  get() = repository.allProducts.asLiveData()

}