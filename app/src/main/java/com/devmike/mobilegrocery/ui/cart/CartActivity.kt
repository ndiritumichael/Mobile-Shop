package com.devmike.mobilegrocery.ui.cart

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast

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
    var checkoutAmount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cart"
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
        binding.checkout.setOnClickListener {
            checkOut()
        }
    }

    private fun checkOut() {
        if (checkoutAmount!= 0.0){

            Toast.makeText(this,"$checkoutAmount",Toast.LENGTH_LONG).show()
        }

    }

    private fun deleteItem(id: Int) {

        viewmodel.deleteOrder(id)

    }


    fun totalCost(orderItemsList: List<OrdersItem>) {
        var total: Double = 0.0
        for (i in orderItemsList) {
            val orderItem: OrdersItem = i
            total += orderItem.quantity.toDouble() * orderItem.unitPrice!!
            checkoutAmount=total
        }
        if (total == 0.0){
            binding.tvCartTotal.visibility = View.GONE
        }
        val totalItems = "Subtotal( ${orderItemsList.size} Order(s) ): $$total"
        binding.tvCartTotal.text = totalItems
    }



}