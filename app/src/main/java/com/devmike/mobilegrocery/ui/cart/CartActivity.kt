package com.devmike.mobilegrocery.ui.cart

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmike.mobilegrocery.adapters.CartItemAdapter

import com.devmike.mobilegrocery.databinding.CartActivityBinding
import com.devmike.mobilegrocery.models.OrdersItem

import com.devmike.mobilegrocery.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private val viewmodel: MainViewModel by viewModels()
    private val binding: CartActivityBinding by viewBinding()
    private lateinit var adapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adapter = CartItemAdapter { id ->
            deleteItem(id)
        }
        setUpUI()


    }

    private fun setUpUI() {
        binding.cartRecycler.layoutManager = LinearLayoutManager(this)
        binding.cartRecycler.adapter = adapter
        viewmodel.allOrdersItem.observe(this) { list ->
            adapter.submitList(list)
        }
        viewmodel.allOrdersItem.observe(this){ ordersItem ->
            totalCost(ordersItem)
        }
    }

    private fun deleteItem(id: Int) {
        Log.d("cart","Adapter Stuff Clicked")
        viewmodel.deleteOrder(id)

    }


    fun totalCost(orderItemsList: List<OrdersItem>) {
        var total: Double = 0.0
        for (i in orderItemsList) {
            val orderItem: OrdersItem = i
            total += orderItem.quantity.toDouble() * orderItem.unitPrice!!
        }
        binding.tvCartTotal.text = "$" + total.toString()
    }



}