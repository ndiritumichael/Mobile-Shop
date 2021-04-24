package com.devmike.mobilegrocery.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.viewbinding.library.activity.viewBinding
import androidx.activity.viewModels
import com.devmike.mobilegrocery.BuildConfig
import com.devmike.mobilegrocery.R

import com.devmike.mobilegrocery.databinding.MainActivityBinding
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.ui.cart.CartActivity
import com.devmike.mobilegrocery.ui.main.MainViewModel
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   // val consumerKey = BuildConfig.
    private val binding: MainActivityBinding by viewBinding()
    lateinit var itemCount : MaterialTextView
    private val viewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.inflateMenu(R.menu.cart_menu)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu,menu)
       val menuItem = menu?.findItem(R.id.action_cart)
        val actionView = menuItem?.actionView
        if (actionView != null) {
            itemCount = actionView.findViewById(R.id.cart_badge)

        }
actionView?.setOnClickListener {
    onOptionsItemSelected(menuItem)
    val intent  = Intent(this,CartActivity::class.java)
 startActivity(intent)


}
        viewModel.allOrdersItem.observe(this){ list ->
            if(list.isEmpty()){
                itemCount.text = "0"
            } else{

                itemCount(list)
            }

        }
        return true

    }

/*
   override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
           R.id.action_cart -> {

               Toast.makeText(this, "Mike trials", Toast.LENGTH_LONG).show()


               true
           }

           else -> return false
       }

       }*/


    private fun itemCount(orderItemsList:List<OrdersItem>) {
        var total = 0
        for (i in orderItemsList) {
            val orderItem: OrdersItem = i
            total += orderItem.quantity
        }
        itemCount.text = total.toString()

    }
    }
/*
    private fun setUpNavigation() {



        binding.toolbar.setOnMenuItemClickListener { menuitem ->
            when(menuitem.itemId){
                R.id.action_cart ->{

                    Toast.makeText(this,"Mike trials",Toast.LENGTH_LONG).show()


                    true
                }
                else -> false
            }

        }


    }*/

