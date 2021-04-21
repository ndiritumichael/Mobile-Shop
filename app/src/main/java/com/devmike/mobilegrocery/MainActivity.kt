package com.devmike.mobilegrocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.devmike.mobilegrocery.R.color.white

import com.devmike.mobilegrocery.databinding.MainActivityBinding
import com.devmike.mobilegrocery.ui.main.MainViewModel
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
            itemCount.text ="5"
        }
actionView?.setOnClickListener {
    onOptionsItemSelected(menuItem)


}
        return true

    }


   override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when (item.itemId) {
           R.id.action_cart -> {

               Toast.makeText(this, "Mike trials", Toast.LENGTH_LONG).show()


               true
           }

           else -> return false
       }

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

